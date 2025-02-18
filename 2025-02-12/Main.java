public class Main {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart(101);

        Product p1 = new TV("televisao", 2000.00, 32);
        Product p2 = new Refrigerator("geladeira", 1500.00, 140);
        Product p3 = new Stove("fogão", 600.00, 5);

        cart.addProduct(p1);
        cart.addProduct(p2);
        cart.addProduct(p3);

        System.out.println("Conteúdo do carrinho:");
        System.out.println(cart.getContents());

        System.out.println("Total: R$" + cart.getTotalPrice());

        cart.removeProduct(1);
        System.out.println("\nConteúdo do carrinho: (Após remoção do produto no índice 1)");
        System.out.println(cart.getContents());

        System.out.println("Total atualizado: R$" + cart.getTotalPrice());
    }
}