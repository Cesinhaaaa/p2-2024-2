package br.ufal.ic.p2.jackut.code.persistence;

import java.io.*;

/**
 * A classe SerializerAndDeserializer fornece m�todos utilit�rios para
 * serializar objetos para um arquivo e desserializar objetos de um arquivo.
 * Al�m disso, inclui um m�todo para limpar o conte�do de um arquivo serializado.
 */
public class SerializerAndDeserializer {
    /**
     * Construtor padr�o da classe SerializerAndDeserializer.
     * Inicializa uma inst�ncia da classe utilit�ria de serializa��o/desserializa��o.
     * Este construtor n�o requer argumentos e serve como a inst�ncia padr�o para a classe.
     */
    public SerializerAndDeserializer(String filePath) {
        File file = new File(filePath);

        try {
            // Criar a pasta se n�o existir
            if (file.getParentFile().mkdirs()) {
                System.out.println("Pasta criada: " + file.getParentFile().getAbsolutePath());
            }

            // Criar o file
            if (file.createNewFile()) {
                System.out.println("Arquivo criado: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo ou pasta: " + e.getMessage());
        }
    }

    /**
     * Serializa um objeto fornecido e o salva em um caminho de arquivo especificado.
     *
     * @param obj O objeto a ser serializado. Deve implementar a interface Serializable.
     * @param filePath O caminho do arquivo onde o objeto serializado ser� salvo.
     */
    public void serializeObject(Object obj, String filePath) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Desserializa um objeto serializado a partir do caminho de arquivo especificado.
     *
     * @param filePath O caminho do arquivo do qual o objeto ser� desserializado.
     * @return O objeto desserializado ou null se ocorrer uma exce��o durante a desserializa��o.
     */
    public Object deserializeFile(String filePath) {
        File file = new File(filePath);

        // Verifica se o arquivo existe e n�o est� vazio
        if (!file.exists() || file.length() == 0) {
            return null;
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            Object restoredObject = objectInputStream.readObject();

            return restoredObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Limpa o conte�do de um arquivo serializado ao abrir o arquivo para escrita
     * e sobrescrev�-lo com um conte�do vazio.
     *
     * @param filePath O caminho do arquivo a ser limpo.
     */
    public void clearSerializedFile(String filePath) {
        this.serializeObject(null, filePath);
    }
}