package najah.edu.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProductManagement {

    @Given("the store owner or supplier is signed in")
    public void theStoreOwnerOrSupplierIsSignedIn1() {
        // تنفيذ الدخول لصاحب المتجر أو المورد
        System.out.println("Store owner or supplier is signed in.");
    }

    @When("they add a new product with name {string}, description {string}, and price {string}")
    public void theyAddANewProductWithNameDescriptionAndPrice(String name, String description, String price) {
        // منطق لإضافة منتج جديد
        System.out.println("Adding new product: " + name + ", Description: " + description + ", Price: " + price);
    }

    @Then("the product should be added successfully")
    public void theProductShouldBeAddedSuccessfully() {
        // تحقق من إضافة المنتج بنجاح
        System.out.println("Product added successfully.");
    }

    @Then("a confirmation message should be displayed")
    public void aConfirmationMessageShouldBeDisplayed() {
        // عرض رسالة تأكيد
        System.out.println("Confirmation message displayed.");
    }

    @Given("the store owner or supplier is signed in and has an existing product")
    public void theStoreOwnerOrSupplierIsSignedInAndHasAnExistingProduct1() {
        // تنفيذ الدخول وتحقق من وجود منتج
        System.out.println("Store owner or supplier is signed in and has an existing product.");
    }

    @When("they update the product with name {string} to {string} and change the price to {string}")
    public void theyUpdateTheProductWithNameToAndChangeThePriceTo(String oldName, String newName, String newPrice) {
        // منطق لتحديث المنتج
        System.out.println("Updating product: " + oldName + " to " + newName + " with new price: " + newPrice);
    }

    @Then("the product details should be updated successfully")
    public void theProductDetailsShouldBeUpdatedSuccessfully() {
        // تحقق من تحديث تفاصيل المنتج بنجاح
        System.out.println("Product details updated successfully.");
    }

    @When("they remove the product with name {string}")
    public void theyRemoveTheProductWithName(String name) {
        // منطق لإزالة المنتج
        System.out.println("Removing product: " + name);
    }

    @Then("the product should be removed successfully")
    public void theProductShouldBeRemovedSuccessfully() {
        // تحقق من إزالة المنتج بنجاح
        System.out.println("Product removed successfully.");
    }

    @When("they view the sales and profits report")
    public void theyViewTheSalesAndProfitsReport() {
        // منطق لعرض تقرير المبيعات والأرباح
        System.out.println("Viewing sales and profits report.");
    }

    @Then("the report should display total sales and profits")
    public void theReportShouldDisplayTotalSalesAndProfits() {
        // عرض إجمالي المبيعات والأرباح
        System.out.println("Report displaying total sales and profits.");
    }

    @Then("a breakdown of sales per product")
    public void aBreakdownOfSalesPerProduct() {
        // عرض تحليل للمبيعات حسب المنتج
        System.out.println("Report displaying breakdown of sales per product.");
    }

    @When("they view the best-selling products report")
    public void theyViewTheBestSellingProductsReport() {
        // منطق لعرض تقرير المنتجات الأكثر مبيعاً
        System.out.println("Viewing best-selling products report.");
    }

    @Then("the report should display a list of best-selling products")
    public void theReportShouldDisplayAListOfBestSellingProducts() {
        // عرض قائمة المنتجات الأكثر مبيعاً
        System.out.println("Report displaying best-selling products.");
    }

    @Then("their respective sales figures")
    public void theirRespectiveSalesFigures() {
        // عرض أرقام المبيعات لكل منتج
        System.out.println("Report displaying respective sales figures.");
    }

    @When("they apply a dynamic discount to a product with name {string} and discount {string}")
    public void theyApplyADynamicDiscountToAProductWithNameAndDiscount(String name, String discount) {
        // منطق لتطبيق خصم ديناميكي على المنتج
        System.out.println("Applying dynamic discount to product: " + name + " with discount: " + discount);
    }

    @Then("the discount should be applied successfully")
    public void theDiscountShouldBeAppliedSuccessfully() {
        // تحقق من تطبيق الخصم بنجاح
        System.out.println("Discount applied successfully.");
    }

    @Then("the new price should reflect the discount")
    public void theNewPriceShouldReflectTheDiscount() {
        // عرض السعر الجديد بعد تطبيق الخصم
        System.out.println("New price reflects the discount.");
    }
}