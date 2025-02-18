public class Main {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart(101);

        Product p1 = new Product("Notebook", 3500.00);
        Product p2 = new Product("Mouse", 150.00);
        Product p3 = new Product("Teclado", 200.00);

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