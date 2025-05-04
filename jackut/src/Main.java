import easyaccept.EasyAccept;

/**
 * A classe Main atua como o ponto de entrada para a execução da aplicação.
 */
public class Main {
    /**
     * O método main funciona como o ponto de entrada para a execução dos testes
     * da aplicação utilizando o framework EasyAccept. Ele inicializa a classe Facade
     * e a lista de arquivos de teste, itera sobre o array de arquivos de teste e executa cada teste.
     *
     * @param args argumentos da linha de comando passados para a aplicação, embora não sejam
     *             explicitamente utilizados.
     */
    public static void main(String[] args) {
        String facade = "br.ufal.ic.p2.jackut.Facade";
        String[] tests = {
                "tests/us1_1.txt",
                "tests/us1_2.txt",
                "tests/us2_1.txt",
                "tests/us2_2.txt",
                "tests/us3_1.txt",
                "tests/us3_2.txt",
                "tests/us4_1.txt",
                "tests/us4_2.txt",
                "tests/us5_1.txt",
                "tests/us5_2.txt",
                "tests/us6_1.txt",
                "tests/us6_2.txt",
                "tests/us7_1.txt",
                "tests/us7_2.txt",
                "tests/us8_1.txt",
                "tests/us8_2.txt",
                "tests/us9_1.txt",
                "tests/us9_2.txt",
        };

        for (String test : tests) {
            String[] argsEA = {facade, test};
            EasyAccept.main(argsEA);
        }
    }
}