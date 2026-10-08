import java.time.Duration;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

class Questao02Test {
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
    void deveAguardarCarregamentoEExibirHelloWorld() {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        driver.findElement(By.cssSelector("#start button")).click();

        WebElement carregamento = driver.findElement(By.id("loading"));
        assertTrue(wait.until(ExpectedConditions.visibilityOf(carregamento)).isDisplayed(), "O indicador de carregamento deve aparecer");
        assertTrue(wait.until(ExpectedConditions.invisibilityOf(carregamento)), "O indicador deve desaparecer ao concluir o carregamento");

        WebElement resultado = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#finish h4")));
        assertEquals("Hello World!", resultado.getText().trim());
    }
}