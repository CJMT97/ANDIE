package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.SobelFilters;

public class SobelFiltersTest {

    @Test
    void getKernelTest() {
        SobelFilters s1 = new SobelFilters(true);
        SobelFilters s2 = new SobelFilters(false);

        float[] expectedKernalIfTrue = { -0.5f, 0, 0.5f, -1f, 0, 1f, -0.5f, 0, 0.5f };
        float[] expectedKernalIfFalse = { -0.5f, -1f, -0.5f, 0, 0, 0, 0.5f, 1f, 0.5f };

        float[] actualKernalIfTrue = s1.getKernel();
        float[] actualKernalIfFalse = s2.getKernel();

        Assertions.assertArrayEquals(expectedKernalIfTrue, actualKernalIfTrue, 0.001f);
        Assertions.assertArrayEquals(expectedKernalIfFalse, actualKernalIfFalse, 0.001f);

    }

}
