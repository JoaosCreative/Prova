describe("Login", () => {
  it("exibe uma mensagem de erro com credenciais vazias", () => {
    cy.visit("/login");
    cy.contains("button", "Login").click();

    cy.get("div#flash")
      .should("be.visible")
      .and("contain.text", "Your username is invalid!")
      .and("have.class", "error");
  });
});