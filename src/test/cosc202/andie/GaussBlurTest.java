package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.GaussBlur;

public class GaussBlurTest {
    @Test
    void getGaussKernelTest() {
        GaussBlur gb = new GaussBlur(1);
        float[] radius1 = new float[] { (float) 0.000, (float) 0.011, (float) 0.000,
                (float) 0.011, (float) 0.957, (float) 0.011,
                (float) 0.000, (float) 0.011, (float) 0.000 };

        float[] radius2 = new float[] { 0.000f, 0.001f, 0.004f, 0.001f, 0.000f,
                0.001f, 0.038f, 0.116f, 0.038f, 0.001f,
                0.004f, 0.116f, 0.358f, 0.116f, 0.004f,
                0.001f, 0.038f, 0.116f, 0.038f, 0.001f,
                0.000f, 0.001f, 0.004f, 0.001f, 0.000f,
        };

        float[] radius3 = new float[] { 0.000f, 0.000f, 0.001f, 0.002f, 0.001f, 0.000f, 0.000f,
                0.000f, 0.003f, 0.013f, 0.022f, 0.013f, 0.003f, 0.000f,
                0.001f, 0.013f, 0.059f, 0.097f, 0.059f, 0.013f, 0.001f,
                0.002f, 0.022f, 0.097f, 0.159f, 0.097f, 0.022f, 0.002f,
                0.001f, 0.013f, 0.059f, 0.097f, 0.059f, 0.013f, 0.001f,
                0.000f, 0.003f, 0.013f, 0.022f, 0.013f, 0.003f, 0.000f,
                0.000f, 0.000f, 0.001f, 0.002f, 0.001f, 0.000f, 0.000f,
        };

        for (int i = 0; i < radius1.length; i++) {
            Assertions.assertEquals(radius1[i], gb.getGaussKernel()[i], 0.001);
        }

        gb.setRadius(2);
        for (int i = 0; i < radius2.length; i++) {
            Assertions.assertEquals(radius2[i], gb.getGaussKernel()[i], 0.001);
        }

        gb.setRadius(3);
        for (int i = 0; i < radius3.length; i++) {
            Assertions.assertEquals(radius3[i], gb.getGaussKernel()[i], 0.001);
        }
    }

    @Test
    void getGaussTest(){
        int radius = 1;
        GaussBlur gb = new GaussBlur(radius);
        for(int i = -1; i<2; i++){
            for(int j = -1; j<2; j++){
                float sigma = radius / 3.0f;
                float sigmaSquared = sigma * sigma;
                float result = (float) ((1 / (2 * Math.PI * sigmaSquared)) * Math.exp(-1 * ((Math.pow(i, 2) + Math.pow(j, 2)) / (2 * sigmaSquared))));
                Assertions.assertEquals(result, gb.getGauss(i, j));
            }
        }

        radius = 5;
        gb.setRadius(radius);
        for(int i = -1; i<2; i++){
            for(int j = -1; j<2; j++){
                float sigma = radius / 3.0f;
                float sigmaSquared = sigma * sigma;
                float result = (float) ((1 / (2 * Math.PI * sigmaSquared)) * Math.exp(-1 * ((Math.pow(i, 2) + Math.pow(j, 2)) / (2 * sigmaSquared))));
                Assertions.assertEquals(result, gb.getGauss(i, j));
            }
        }

        radius = 10;
        gb.setRadius(radius);
        for(int i = -1; i<2; i++){
            for(int j = -1; j<2; j++){
                float sigma = radius / 3.0f;
                float sigmaSquared = sigma * sigma;
                float result = (float) ((1 / (2 * Math.PI * sigmaSquared)) * Math.exp(-1 * ((Math.pow(i, 2) + Math.pow(j, 2)) / (2 * sigmaSquared))));
                Assertions.assertEquals(result, gb.getGauss(i, j));
            }
        }
        
    }
}
