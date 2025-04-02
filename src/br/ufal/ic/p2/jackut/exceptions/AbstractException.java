package br.ufal.ic.p2.jackut.exceptions;

/**
 * AbstractException serve como a classe base para todos os tipos de exceção personalizados no aplicativo.
 * Ela estende a classe Exception e fornece uma estrutura comum para tratar mensagens de erro de forma consistente.
 *
 * Espera-se que as subclasses de AbstractException representem erros específicos passando mensagens apropriadas para o construtor da superclasse.
 */
public abstract class AbstractException extends Exception {
    /**
     * Constrói uma nova AbstractException com a mensagem especificada.
     *
     * @param message a mensagem a ser associada à exceção
     */
    public AbstractException(String message) {
        super(message);
    }

    /**
     * Retorna a cadeia de caracteres da mensagem dessa exceção.
     *
     * @return a mensagem dessa exceção como uma String.
     */
    @Override
    public String toString() {
        return getMessage();
    }
}