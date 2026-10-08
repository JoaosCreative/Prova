from playwright.sync_api import Page, expect

def testar_mensagem_erro_login(page: Page):
    # Acessa a página de login com timeout estendido
    page.goto("https://the-internet.herokuapp.com/login", timeout=60000)
    
    # Clica no botão de Login sem preencher os campos
    page.get_by_role("button", name="Login").click()
    
    # Valida a mensagem e a classe de erro
    flash_message = page.locator("div#flash")
    expect(flash_message).to_be_visible()
    expect(flash_message).to_contain_text("Your username is invalid!")
    expect(flash_message).toHave_class("flash error")
