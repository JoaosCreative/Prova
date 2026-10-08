describe('Teste de Carregamento Dinâmico', () => {
  it('Deve clicar em Start, aguardar o loading sumir e exibir Hello World', () => {
    cy.visit('https://the-internet.herokuapp.com/dynamic_loading/1')
    cy.get('#start button').click()
    cy.get('#loading').should('not.be.visible')
    cy.get('#finish h4').should('have.text', 'Hello World!')
  })
})
