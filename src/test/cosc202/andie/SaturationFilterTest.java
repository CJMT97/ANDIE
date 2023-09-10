package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.SaturationFilter;

public class SaturationFilterTest {

    @Test
    void setSaturationTest() {

        SaturationFilter filter = new SaturationFilter();
        float actual = filter.getSaturation();
        System.out.println(actual);
        Assertions.assertTrue(-10 <= actual && 10 >= actual);

    }

}
