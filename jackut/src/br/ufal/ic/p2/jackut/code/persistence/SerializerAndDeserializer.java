package br.ufal.ic.p2.jackut.code.persistence;

import java.io.*;

/**
 * A classe SerializerAndDeserializer fornece métodos utilitários para
 * serializar objetos para um arquivo e desserializar objetos de um arquivo.
 * Além disso, inclui um método para limpar o conteúdo de um arquivo serializado.
 */
public class SerializerAndDeserializer {
    /**
     * Construtor padrão da classe SerializerAndDeserializer.
     * Inicializa uma instância da classe utilitária de serialização/desserialização.
     * Este construtor não requer argumentos e serve como a instância padrão para a classe.
     */
    public SerializerAndDeserializer(String filePath) {
        File file = new File(filePath);

        try {
            // Criar a pasta se não existir
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
     * @param filePath O caminho do arquivo onde o objeto serializado será salvo.
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
     * @param filePath O caminho do arquivo do qual o objeto será desserializado.
     * @return O objeto desserializado ou null se ocorrer uma exceção durante a desserialização.
     */
    public Object deserializeFile(String filePath) {
        File file = new File(filePath);

        // Verifica se o arquivo existe e não está vazio
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
     * Limpa o conteúdo de um arquivo serializado ao abrir o arquivo para escrita
     * e sobrescrevê-lo com um conteúdo vazio.
     *
     * @param filePath O caminho do arquivo a ser limpo.
     */
    public void clearSerializedFile(String filePath) {
        this.serializeObject(null, filePath);
    }
}