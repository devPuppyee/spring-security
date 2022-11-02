package com.seolhan.demospringsecurityform.account;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Test
    @Transactional
    public void login() throws Exception {
        String userName = "seolhoney";
        String password = "123";
        String role = "USER";
        // given
        Account user = this.createUser(userName,password,role);
        // when, then
        mockMvc.perform(formLogin().user(user.getUsername()).password(password))
                .andExpect(authenticated());
    }

    private Account createUser(String userName, String password, String role) {
        Account account = new Account();
        account.setUsername(userName);
        account.setPassword(password);
        account.setRole(role);
        return accountService.createAccount(account);
    }

    @Test
    @DisplayName("index 페이지에 인증되지 않은 상태로 접근")
    @WithAnonymousUser
    public void index_anonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("dashboard 페이지에 특정 사용자로 접근")
    @WithUser
    public void dashboard_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dashboard"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("admin 페이지에 일반유저 사용자로 접근")
    @WithUser
    public void admin_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin").with(user("seolhoney").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
