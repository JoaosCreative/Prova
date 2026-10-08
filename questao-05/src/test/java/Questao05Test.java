import java.math.BigDecimal;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

import static org.junit.jupiter.api.Assertions.assertFalse;

class Questao05Test {
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
    void deveExibirProdutosComPrecoAbaixoDeVinteDolaresSemDuplicatas() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.cssSelector("[data-test='username']")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("[data-test='password']")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("[data-test='login-button']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='inventory-list']")));

        List<WebElement> itens = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[data-test='inventory-item']")));
        Map<String, BigDecimal> produtosUnicos = new LinkedHashMap<>();
        for (WebElement item : itens) {
            String nome = item.findElement(By.cssSelector("[data-test='inventory-item-name']")).getText().trim();
            String textoPreco = item.findElement(By.cssSelector("[data-test='inventory-item-price']")).getText();
            BigDecimal preco = new BigDecimal(textoPreco.replace("$", "").trim());
            produtosUnicos.putIfAbsent(nome, preco);
        }

        List<Map.Entry<String, BigDecimal>> abaixoDoLimite = produtosUnicos.entrySet().stream()
                .filter(produto -> produto.getValue().compareTo(new BigDecimal("20.00")) < 0)
                .toList();

        assertFalse(produtosUnicos.isEmpty(), "A página deve exibir produtos para rastrear");
        if (abaixoDoLimite.isEmpty()) {
            System.out.println("Nenhum produto encontrado abaixo de $20.00.");
        } else {
            abaixoDoLimite.forEach(produto -> System.out.printf("%s - $%s%n", produto.getKey(), produto.getValue().toPlainString()));
        }
    }
}