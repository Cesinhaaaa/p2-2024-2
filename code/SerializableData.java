package br.ufal.ic.p2.jackut.code;

import java.io.Serializable;

/**
 * Uma classe abstrata projetada para encapsular operações de serialização e desserialização de dados,
 * usando um caminho de arquivo para definir a localização dos dados serializados. Esta classe utiliza
 * os métodos de `SerializerAndDeserializer` para realizar operações de I/O. Ela fornece uma base
 * para que subclasses implementem comportamentos específicos do objeto durante a desserialização
 * por meio do método `castObject`.
 *
 * Subclasses de `SerializableData` devem implementar o método `castObject` para definir como
 * um objeto específico da subclasse deve ser convertido durante a desserialização.
 */
public abstract class SerializableData implements Serializable {
    private final String filePath;
    private final transient SerializerAndDeserializer objSer;
    private static final long serialVersionUID = 1l;

    /**
     * Constrói uma nova instância de SerializableData com o caminho de arquivo especificado.
     * Inicializa um objeto interno de SerializerAndDeserializer para gerenciar operações
     * de serialização e desserialização.
     *
     * @param filePath o caminho para o arquivo onde os dados serializados serão armazenados ou recuperados
     */
    public SerializableData(String filePath) {
        this.filePath = filePath;
        this.objSer = new SerializerAndDeserializer(filePath);
    }

    /**
     * Limpa o conteúdo do arquivo serializado associado a este objeto.
     *
     * Este método invoca o método `clearSerializedFile` da classe
     * `SerializerAndDeserializer` para remover todo o conteúdo do arquivo
     * especificado pelo atributo `filePath`. O arquivo permanece no sistema
     * de arquivos, mas seu conteúdo é efetivamente esvaziado.
     *
     * Esta operação é final para garantir um comportamento consistente de limpeza
     * para todas as subclasses de `SerializableData`.
     */
    final public void clearSerialized() {
        objSer.clearSerializedFile(filePath);
    }

    /**
     * Serializa o estado atual do objeto para o arquivo especificado pelo caminho do arquivo.
     * Este método utiliza a utilidade `SerializerAndDeserializer` para gravar o objeto
     * em um arquivo em forma serializada, permitindo que ele seja persistido e posteriormente
     * desserializado.
     *
     * Esta operação é final para garantir um comportamento consistente de serialização
     * para todas as subclasses de `SerializableData`.
     */
    final public void serialize() {
        objSer.serializeObject(this, filePath);
    }

    /**
     * Define como um objeto, obtido durante a desserialização, deve ser convertido
     * para um tipo apropriado. Este método deve ser implementado pelas subclasses
     * para lidar com a lógica específica de conversão para seus dados.
     *
     * @param obj o objeto desserializado que precisa ser convertido para um tipo específico
     */
    protected abstract void castObject(Object obj);

    /**
     * Desserializa um objeto de um arquivo especificado pelo caminho de arquivo atual e o
     * converte para um tipo específico conforme implementado no método `castObject` pelas subclasses.
     *
     * Esta operação é final para garantir um comportamento consistente de desserialização
     * para todas as subclasses de `SerializableData`.
     */
    final public void deserialize() {
        Object obj = objSer.deserializeFile(filePath);
        if (obj != null) {
            castObject(obj);
        }
    }
}