import java.util.ArrayList;

public class ShoppingCart {
    private int customerID;
    private ArrayList<Product> productList;

    public ShoppingCart(int customerID) {
        this.customerID = customerID;
        this.productList = new ArrayList<Product>();
    }

    public void addProduct(Product product) { this.productList.add(product); }
    
    public void removeProduct(Product product) { this.productList.remove(product); }

    public void removeProduct(int productIdx) { this.productList.remove(productIdx); }
    
    public String getContents() {
        String allProducts = "";
        for (Product product : productList) {
            String productInfo = String.format("Product: %s, R$ %.2f\n", product.getName(), product.getPrice());
            allProducts += productInfo;
        }
        return allProducts;
    }

    public int getCustomerID() { return this.customerID; }
    
    public int getItemCount() { return this.productList.size(); }
    
    public double getTotalPrice() {
        double totalPrice = 0;
        for (Product product : productList) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }
}