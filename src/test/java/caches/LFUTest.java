package caches;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class LFUTest {
    LFU<Integer> lfu;
    private static Stream<Arguments> dataArgumentsProvider(){
        return Stream.of(
                Arguments.of("12", 5)
        );
    }

    @BeforeEach
    void init(){
        lfu = new LFU<>(3);
    }

    @ParameterizedTest
    @MethodSource("dataArgumentsProvider")
    void putTest(String key, int value){
        lfu.put(key, value);
        assertThat(lfu.get(key)).isEqualTo(5);
    }

    @Test
    void deletedTest(){
        lfu.put("12", 6);
        lfu.put("14", 5);
        lfu.put("10", 2);
        lfu.put("13", 11);
        assertThat(lfu.get("12")).isNull();
    }

    @ParameterizedTest
    @MethodSource("dataArgumentsProvider")
    void getTest(String key, int value){
        assertThat(lfu.get(key)).isNull();
    }
}