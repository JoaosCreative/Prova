from playwright.sync_api import Page, expect


def test_mensagem_erro_login(page: Page):
    page.goto("https://the-internet.herokuapp.com/login")
    page.get_by_role("button", name="Login").click()
    flash_message = page.locator("div#flash")
    expect(flash_message).to_be_visible()
    expect(flash_message).to_contain_text("Your username is invalid!")
    expect(flash_message).to_have_class("flash error")