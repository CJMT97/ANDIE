package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.EmbossFilters;

public class EmbossFiltersTest {

    @Test
    void getKernalTest() {

        EmbossFilters emboss1 = new EmbossFilters(1);
        float[] expected1 = { 0, 0, 0, 1f, 0, -1f, 0, 0, 0 };
        float[] actual1 = emboss1.getKernel();
        for (int i = 0; i < expected1.length; i++) {
            Assertions.assertEquals(expected1[i], actual1[i], 0.001);
        }

        EmbossFilters emboss2 = new EmbossFilters(2);
        float[] expected2 = { 1f, 0, 0, 0, 0, 0, 0, 0, -1f };
        float[] actual2 = emboss2.getKernel();
        for (int i = 0; i < expected2.length; i++) {
            Assertions.assertEquals(expected2[i], actual2[i], 0.001);
        }

        EmbossFilters emboss3 = new EmbossFilters(3);
        float[] expected3 = { 0, 1f, 0,
                0, 0, 0,
                0, -1f, 0 };
        float[] actual3 = emboss3.getKernel();
        for (int i = 0; i < expected3.length; i++) {
            Assertions.assertEquals(expected3[i], actual3[i], 0.001);
        }

        EmbossFilters emboss4 = new EmbossFilters(4);
        float[] expected4 = { 0, 0, 1f,
                0, 0, 0,
                -1f, 0, 0 };
        float[] actual4 = emboss4.getKernel();
        for (int i = 0; i < expected4.length; i++) {
            Assertions.assertEquals(expected4[i], actual4[i], 0.001);
        }

        EmbossFilters emboss5 = new EmbossFilters(5);
        float[] expected5 = { 0, 0, 0,
                -1f, 0, 1f,
                0, 0, 0 };
        float[] actual5 = emboss5.getKernel();
        for (int i = 0; i < expected5.length; i++) {
            Assertions.assertEquals(expected5[i], actual5[i], 0.001);
        }

        EmbossFilters emboss6 = new EmbossFilters(6);
        float[] expected6 = { -1f, 0, 0,
                0, 0, 0,
                0, 0, 1f };
        float[] actual6 = emboss6.getKernel();
        for (int i = 0; i < expected6.length; i++) {
            Assertions.assertEquals(expected6[i], actual6[i], 0.001);
        }

        EmbossFilters emboss7 = new EmbossFilters(7);
        float[] expected7 = { 0, -1f, 0,
                0, 0, 0,
                0, 1f, 0 };
        float[] actual7 = emboss7.getKernel();
        for (int i = 0; i < expected7.length; i++) {
            Assertions.assertEquals(expected7[i], actual7[i], 0.001);
        }

        // For all other filter types
        EmbossFilters emboss8 = new EmbossFilters(8);
        float[] expected8 = { 0, 0, -1f,
                0, 0, 0,
                1f, 0, 0 };
        float[] actual8 = emboss8.getKernel();
        for (int i = 0; i < expected8.length; i++) {
            Assertions.assertEquals(expected8[i], actual8[i], 0.001);
        }

    }

}