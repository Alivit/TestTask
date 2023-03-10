package service;

import database.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class QueryServiceTest {

    @BeforeEach
    void init(){
        DBConnection.init();
    }

    @ParameterizedTest
    @ValueSource(strings = {"select * from product"})
    void getQueryTest(String query) throws SQLException {
        ResultSet resultSet = QueryService.get(query);
        assertThat(resultSet).isNotNull();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void emptyQueryTest(String query) throws SQLException {
        ResultSet resultSet = QueryService.get(query);
        assertThat(resultSet).isNull();
    }

}