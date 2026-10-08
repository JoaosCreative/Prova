import java.math.BigDecimal;
import java.time.Duration;
import java.util.Comparator;
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

import static org.junit.jupiter.api.Assertions.assertTrue;

class Questao04Test {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void iniciarNavegador() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1440,1000");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterEach
    void encerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void deveIdentificarEListarOLaptopMaisCaroExibido() {
        driver.get("https://www.demoblaze.com/");
        By cards = By.cssSelector("#tbodyid .card");
        WebElement primeiroProduto = wait.until(ExpectedConditions.visibilityOfElementLocated(cards));
        driver.findElement(By.linkText("Laptops")).click();
        wait.until(ExpectedConditions.stalenessOf(primeiroProduto));

        List<WebElement> produtosExibidos = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cards));
        List<Laptop> laptops = produtosExibidos.stream()
                .map(card -> new Laptop(
                        card.findElement(By.cssSelector(".card-title a")).getText().trim(),
                        new BigDecimal(card.findElement(By.cssSelector(".card-block h5")).getText().replace("$", "").trim())
                ))
                .toList();

        Laptop maisCaro = laptops.stream().max(Comparator.comparing(Laptop::preco)).orElseThrow();
        System.out.printf("Laptop mais caro: %s - $%s%n", maisCaro.nome(), maisCaro.preco().toPlainString());
        assertTrue(maisCaro.preco().compareTo(BigDecimal.ZERO) > 0, "O preço do laptop mais caro deve ser maior que zero");
    }

    private record Laptop(String nome, BigDecimal preco) {
    }
}