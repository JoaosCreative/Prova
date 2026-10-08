# Atividade Avaliativa - Desenvolvimento de Sistemas

Projeto acadêmico com cinco testes E2E independentes, implementados com Selenium WebDriver e Java. Cada questão possui seu próprio módulo Maven e pode ser executada separadamente ou junto com as demais pelo agregador da raiz.

## Tecnologias utilizadas

- Java 17 ou superior
- Selenium WebDriver 4
- JUnit 5
- Maven
- Google Chrome em modo headless

O Selenium Manager gerencia o ChromeDriver automaticamente. O navegador Chrome precisa estar instalado; o workflow do GitHub Actions instala o Chrome antes de executar os testes.

## Questões

### Questão 01
Valida a mensagem de erro e a classe CSS no formulário de login. [Pasta da questão](questao-01/)

### Questão 02
Usa espera explícita para validar o resultado do carregamento dinâmico. [Pasta da questão](questao-02/)

### Questão 03
Faz login no SauceDemo, adiciona três produtos ao carrinho e remove um. [Pasta da questão](questao-03/)

### Questão 04
Extrai os laptops exibidos no Demoblaze e identifica o de maior preço. [Pasta da questão](questao-04/)

### Questão 05
Lista os produtos do SauceDemo com preço inferior a $20.00. [Pasta da questão](questao-05/)

## Execução

Para executar todas as questões a partir da raiz:

```sh
mvn test
```

Para executar uma questão específica, entre no diretório do módulo e execute `mvn test`. Por exemplo:

```sh
cd questao-01
mvn test
```

Repita o comando nos diretórios `questao-02` até `questao-05`. Os testes iniciam o Chrome em modo headless e fecham o navegador ao final de cada execução.