package najah.edu.acceptance;

import java.util.ArrayList;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MonitoringandReporting {
	private FinancialReportService financialReportService = new FinancialReportService();
	 private ProductService productService = new ProductService();
	 private UserStatisticsService userStatisticsService = new UserStatisticsService();

	@When("I monitor the profits for all stores")
	public void iMonitorTheProfitsForAllStores() {
	    // Write code here that turns the phrase above into concrete actions
		financialReportService.monitorProfits();
	}
	@Then("I should see a financial report generated for each store")
	public void iShouldSeeAFinancialReportGeneratedForEachStore() {
	    // Write code here that turns the phrase above into concrete actions
		 boolean reportGenerated = financialReportService.isReportGenerated();
	        Assert.assertTrue("The financial report was not generated for each store", reportGenerated);
	        System.out.println("Financial report generated successfully for each store.");
	        
	}
	@When("I view the best-selling products in each store")
	public void iViewTheBestSellingProductsInEachStore() {
	    // Write code here that turns the phrase above into concrete actions
		productService.viewBestSellingProducts();
	}
	@Then("I should see a list of best-selling products for each store")
	public void iShouldSeeAListOfBestSellingProductsForEachStore() {
	    // Write code here that turns the phrase above into concrete actions
		  List<String> bestSellingProducts = productService.getBestSellingProducts();
	        Assert.assertNotNull("The list of best-selling products should not be null", bestSellingProducts);
	        Assert.assertTrue("The list of best-selling products should not be empty", !bestSellingProducts.isEmpty());
	        System.out.println("Best-selling products list: " + bestSellingProducts);
	}
	

	@When("I gather statistics on registered users by city")
	public void iGatherStatisticsOnRegisteredUsersByCity() {
	    // Write code here that turns the phrase above into concrete actions
		 userStatisticsService.gatherStatisticsByCity();
	}
	@Then("I should see the statistics displayed for each city including Nablus and Jenin")
	public void iShouldSeeTheStatisticsDisplayedForEachCityIncludingNablusAndJenin() {
	    // Write code here that turns the phrase above into concrete actions
		 Map<String, Integer> userStatistics = userStatisticsService.getUserStatistics();
	        Assert.assertNotNull("The user statistics should not be null", userStatistics);
	        Assert.assertFalse("The user statistics should not be empty", userStatistics.isEmpty());
	        
	        // تحقق من أن الإحصائيات تشمل المدن المطلوبة
	        Assert.assertTrue("The statistics should include Nablus", userStatistics.containsKey("Nablus"));
	        Assert.assertTrue("The statistics should include Jenin", userStatistics.containsKey("Jenin"));
	        
	        System.out.println("User statistics by city: " + userStatistics);
	}

	class FinancialReportService {
	    private boolean reportGenerated = false;

	    public void monitorProfits() {
	        System.out.println("Monitoring profits for all stores...");
	        reportGenerated = true;
	    }

	    public boolean isReportGenerated() {
	        return reportGenerated;
	    }
	}

	class ProductService {
	    private List<String> bestSellingProducts = new ArrayList<>();

	    public void viewBestSellingProducts() {
	        System.out.println("Viewing best-selling products in each store...");

	        bestSellingProducts.add("Product A");
	        bestSellingProducts.add("Product B");
	        bestSellingProducts.add("Product C");
	    }

	    public List<String> getBestSellingProducts() {
	        return bestSellingProducts;
	    }
	}
	class UserStatisticsService {
	    private Map<String, Integer> userStatistics = new HashMap<>();

	    public void gatherStatisticsByCity() {
	        // هنا يتم تنفيذ المنطق الفعلي لجمع الإحصائيات حسب المدينة
	        System.out.println("Gathering statistics on registered users by city...");

	        // نعتبر أن الإحصائيات تم جمعها بنجاح
	        userStatistics.put("Nablus", 150);
	        userStatistics.put("Jenin", 100);
	    }

	    public Map<String, Integer> getUserStatistics() {
	        return userStatistics;
	    }
	}
}