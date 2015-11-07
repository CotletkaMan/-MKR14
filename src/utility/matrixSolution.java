package utility;

/**
 * Created by cotletkaman on 02.10.14.
 */
public class matrixSolution {
    public  double[] progonka(double[][] arr , double [] b){
        double[] alpha = new double[b.length - 1];
        double[] beta = new double[b.length - 1];
        double[] x = new double[b.length];
        alpha[0] = -1 * arr[0][1] / arr[0][0];
        beta[0] = b[0] / arr[0][0];
        for (int i = 1; i < alpha.length; i++) {
            alpha[i] = (-1 * arr[i][1 + i]) / (arr[i][i - 1] * alpha[i - 1] + arr[i][i]);
            beta[i] = (b[i] - arr[i][i - 1] * beta[i - 1]) / (arr[i][i - 1] * alpha[i - 1] + arr[i][i]);
        }
        int key  = x.length - 1;
        x[key] = (b[key] - arr[key][key - 1] * beta[key - 1]) / (arr[key][key - 1] * alpha[key - 1] + arr[key][key]);
        for(int i = x.length - 2 ; i >= 0; i--){
            x[i] = alpha[i] * x[i + 1] + beta[i];
        }
        return x;
    }
    public static  double[] gauss(double[][] arr , double[] b) {
        double[] x = new double[b.length];
        createAngleMatrix(arr , b);
        for(int i = arr.length - 1 ; i >= 0 ; i--){
            for(int j = arr[i].length - 1 ; j >= i ; j--){
                b[i] -= arr[i][j] * x[j];
                if(i == j)
                    x[i] += b[i] / arr[i][j];
            }
        }
        return x;
    }

    public static double[] gauss(double[][] arr){
        double[] x = new double[arr.length];
        createAngleMatrix(arr);
        for(int i = arr.length - 1 ; i >= 0 ; i--){
            for(int j = arr[i].length - 2 ; j >= i ; j--){
                arr[i][arr[i].length - 1] -= arr[i][j] * x[j];
                if(i == j)
                    x[i] += arr[i][arr[i].length - 1] / arr[i][j];
            }
        }
        return x;
    }

    public static void createAngleMatrix(double[][] arr){
        for (int i = 0; i < arr.length - 1; i++) {
            for (int k = i + 1; k < arr.length; k++) {
                double key = arr[k][i] / arr[i][i];
                if(key != 0)
                    for (int j = i; j < arr[i].length; j++)
                        arr[k][j] -= arr[i][j] * key;
            }
        }
    }

    public static void createAngleMatrix(double[][] arr , double[] b){
        for (int i = 0; i < arr.length - 1; i++) {
            for (int k = i + 1; k < arr.length; k++) {
                double key = arr[k][i] / arr[i][i];
                if(key != 0) {
                    for (int j = i; j < arr[i].length; j++)
                        arr[k][j] -= arr[i][j] * key;
                    b[k] -= b[i] * key;
                }
            }
        }
    }
}
