import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Questao03Test {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void iniciarNavegador() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1440,1000");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterEach
    void encerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void deveAdicionarTresProdutosRemoverUmEManterDoisNoCarrinho() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.cssSelector("[data-test='username']")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("[data-test='password']")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("[data-test='login-button']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='inventory-list']")));

        List<String> produtos = List.of("sauce-labs-backpack", "sauce-labs-bike-light", "sauce-labs-bolt-t-shirt");
        for (String produto : produtos) {
            driver.findElement(By.cssSelector("[data-test='add-to-cart-" + produto + "']")).click();
        }

        WebElement badge = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='shopping-cart-badge']")));
        wait.until(ExpectedConditions.textToBePresentInElement(badge, "3"));
        driver.findElement(By.cssSelector("[data-test='shopping-cart-link']")).click();

        By itensNoCarrinho = By.cssSelector("[data-test='inventory-item']");
        wait.until(ExpectedConditions.numberOfElementsToBe(itensNoCarrinho, 3));
        driver.findElement(By.cssSelector("[data-test='remove-sauce-labs-backpack']")).click();

        wait.until(ExpectedConditions.textToBe(By.cssSelector("[data-test='shopping-cart-badge']"), "2"));
        wait.until(ExpectedConditions.numberOfElementsToBe(itensNoCarrinho, 2));
        assertEquals("2", driver.findElement(By.cssSelector("[data-test='shopping-cart-badge']")).getText().trim());
        assertEquals(2, driver.findElements(itensNoCarrinho).size());
    }
}