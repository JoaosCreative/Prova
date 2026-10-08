import java.util.Arrays;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Questao01Test {
    private WebDriver driver;

    @BeforeEach
    void iniciarNavegador() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1440,1000");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void encerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void deveExibirMensagemDeUsuarioInvalidoComClasseDeErro() {
        driver.get("https://the-internet.herokuapp.com/login");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement mensagem = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
        String texto = mensagem.getText();
        String[] classes = mensagem.getAttribute("class").split("\\s+");

        assertAll(
                () -> assertTrue(texto.contains("Your username is invalid!"), "A mensagem de erro esperada deve ser exibida"),
                () -> assertTrue(Arrays.asList(classes).contains("error"), "A mensagem deve possuir a classe CSS error")
        );
    }
}