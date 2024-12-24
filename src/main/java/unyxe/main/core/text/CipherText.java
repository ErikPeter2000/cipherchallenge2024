package main.core.text;

import main.utils.Analyser;
import main.utils.FitnessCalculator;

public class CipherText extends Text{
    Alphabet plainTextAlphabet;
    public CipherText(){
        super();
    }
    public CipherText(String text){
        super(text);
    }
    public double getMonogramABVFitness(){
        return FitnessCalculator.MonogramABVFitness(this.byteTextData);
    }
    public double getMonogramChiFitness(){
        return FitnessCalculator.MonogramChiFitness(this.byteTextData);
    }
    public double getTetragramFitness(){
        return FitnessCalculator.TetragramFitness(this.byteTextData);
    }
    public double getIOC(){
        return Analyser.getIndexOfCoincidence(this.byteTextData, false);
    }
    public double getIOC(boolean returnNormalised){
        return Analyser.getIndexOfCoincidence(this.byteTextData, returnNormalised);
    }
    public double getEntropy(){
        return Analyser.getEntropy(this.byteTextData);
    }
}
