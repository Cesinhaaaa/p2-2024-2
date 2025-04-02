package br.ufal.ic.p2.jackut.code;

import java.io.Serializable;

/**
 * Uma classe abstrata projetada para encapsular opera��es de serializa��o e desserializa��o de dados,
 * usando um caminho de arquivo para definir a localiza��o dos dados serializados. Esta classe utiliza
 * os m�todos de `SerializerAndDeserializer` para realizar opera��es de I/O. Ela fornece uma base
 * para que subclasses implementem comportamentos espec�ficos do objeto durante a desserializa��o
 * por meio do m�todo `castObject`.
 *
 * Subclasses de `SerializableData` devem implementar o m�todo `castObject` para definir como
 * um objeto espec�fico da subclasse deve ser convertido durante a desserializa��o.
 */
public abstract class SerializableData implements Serializable {
    private final String filePath;
    private final transient SerializerAndDeserializer objSer;
    private static final long serialVersionUID = 1l;

    /**
     * Constr�i uma nova inst�ncia de SerializableData com o caminho de arquivo especificado.
     * Inicializa um objeto interno de SerializerAndDeserializer para gerenciar opera��es
     * de serializa��o e desserializa��o.
     *
     * @param filePath o caminho para o arquivo onde os dados serializados ser�o armazenados ou recuperados
     */
    public SerializableData(String filePath) {
        this.filePath = filePath;
        this.objSer = new SerializerAndDeserializer(filePath);
    }

    /**
     * Limpa o conte�do do arquivo serializado associado a este objeto.
     *
     * Este m�todo invoca o m�todo `clearSerializedFile` da classe
     * `SerializerAndDeserializer` para remover todo o conte�do do arquivo
     * especificado pelo atributo `filePath`. O arquivo permanece no sistema
     * de arquivos, mas seu conte�do � efetivamente esvaziado.
     *
     * Esta opera��o � final para garantir um comportamento consistente de limpeza
     * para todas as subclasses de `SerializableData`.
     */
    final public void clearSerialized() {
        objSer.clearSerializedFile(filePath);
    }

    /**
     * Serializa o estado atual do objeto para o arquivo especificado pelo caminho do arquivo.
     * Este m�todo utiliza a utilidade `SerializerAndDeserializer` para gravar o objeto
     * em um arquivo em forma serializada, permitindo que ele seja persistido e posteriormente
     * desserializado.
     *
     * Esta opera��o � final para garantir um comportamento consistente de serializa��o
     * para todas as subclasses de `SerializableData`.
     */
    final public void serialize() {
        objSer.serializeObject(this, filePath);
    }

    /**
     * Define como um objeto, obtido durante a desserializa��o, deve ser convertido
     * para um tipo apropriado. Este m�todo deve ser implementado pelas subclasses
     * para lidar com a l�gica espec�fica de convers�o para seus dados.
     *
     * @param obj o objeto desserializado que precisa ser convertido para um tipo espec�fico
     */
    protected abstract void castObject(Object obj);

    /**
     * Desserializa um objeto de um arquivo especificado pelo caminho de arquivo atual e o
     * converte para um tipo espec�fico conforme implementado no m�todo `castObject` pelas subclasses.
     *
     * Esta opera��o � final para garantir um comportamento consistente de desserializa��o
     * para todas as subclasses de `SerializableData`.
     */
    final public void deserialize() {
        Object obj = objSer.deserializeFile(filePath);
        if (obj != null) {
            castObject(obj);
        }
    }
}