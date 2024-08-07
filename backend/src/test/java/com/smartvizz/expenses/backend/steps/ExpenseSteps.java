package com.smartvizz.expenses.backend.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.smartvizz.expenses.backend.model.Expense;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ExpenseSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult mvcResult;

    @Given("the application is running")
    public void theApplicationIsRunning() {
        // No setup needed as @SpringBootTest ensures the app context is loaded
    }

    @When("the user creates a new expense with the following details")
    public void theUserCreatesANewExpenseWithTheFollowingDetails(io.cucumber.datatable.DataTable dataTable) throws Exception {
        List<List<String>> rows = dataTable.asLists(String.class);
        for (List<String> columns : rows) {
            Expense expense = new Expense(columns.get(0), columns.get(1), Double.parseDouble(columns.get(2)), columns.get(3));

            mvcResult = mockMvc.perform(post("/api/expenses")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(expense)))
                    .andExpect(status().isOk())
                    .andReturn();
        }
    }

    @Then("the expense should be created successfully")
    public void theExpenseShouldBeCreatedSuccessfully() throws Exception {
        String responseContent = mvcResult.getResponse().getContentAsString();
        Expense createdExpense = objectMapper.readValue(responseContent, Expense.class);
        assertThat(createdExpense.getTitle()).isEqualTo("śniadanie");
    }

    @When("the user lists all expenses")
    public void theUserListsAllExpenses() throws Exception {
        mvcResult = mockMvc.perform(get("/api/expenses"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Then("the response should contain the following expenses")
    public void theResponseShouldContainTheFollowingExpenses(io.cucumber.datatable.DataTable dataTable) throws Exception {
        String responseContent = mvcResult.getResponse().getContentAsString();
        List<Expense> expenses = objectMapper.readValue(responseContent, new TypeReference<List<Expense>>() {});

        List<List<String>> expectedExpenses = dataTable.asLists(String.class);
        for (int i = 0; i < expectedExpenses.size(); i++) {
            List<String> expected = expectedExpenses.get(i);
            Expense actual = expenses.get(i);

            assertThat(actual.getId()).isEqualTo(Integer.parseInt(expected.get(0)));
            assertThat(actual.getTitle()).isEqualTo(expected.get(1));
            assertThat(actual.getDescription()).isEqualTo(expected.get(2));
            assertThat(actual.getAmount()).isEqualTo(Double.parseDouble(expected.get(3)));
            assertThat(actual.getCurrency()).isEqualTo(expected.get(4));
        }
    }
}
