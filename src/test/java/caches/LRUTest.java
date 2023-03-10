package caches;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class LRUTest {
    LRU<Integer> lru;
    private static Stream<Arguments> dataArgumentsProvider(){
        return Stream.of(
                Arguments.of("12", 5)
        );
    }

    @BeforeEach
    void init(){
        lru = new LRU<>(3);
    }

    @ParameterizedTest
    @MethodSource("dataArgumentsProvider")
    void putTest(String key, int value){
        lru.put(key, value);
        assertThat(lru.get(key)).isEqualTo(5);
    }

    @Test
    void deletedTest(){
        lru.put("12", 6);
        lru.put("14", 5);
        lru.put("10", 2);
        lru.put("13", 11);
        assertThat(lru.get("12")).isNull();
    }

    @ParameterizedTest
    @MethodSource("dataArgumentsProvider")
    void getTest(String key, int value){
        assertThat(lru.get(key)).isNull();
    }

}