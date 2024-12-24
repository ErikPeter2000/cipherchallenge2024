package main.core.text;

import main.utils.TextUtilities;

public class Text {
    String stringTextData;
    byte[] byteTextData;
    Byte[] byteTextDataObject;



    Alphabet alphabet;



    public Text(){
        setString("");
    }
    public Text(String text){
        setString(text);
    }
    public Text(byte[] text, Alphabet alphabet){
        setBytes(text, alphabet);
    }
    public Text(Byte[] text, Alphabet alphabet){
        setBytesObject(text, alphabet);
    }



    public String getString() {
        return stringTextData;
    }
    public byte[] getBytes() {
        return byteTextData;
    }
    public Byte[] getBytesObject() {
        return byteTextDataObject;
    }



    public void setString(String text){
        stringTextData = text;
        this.alphabet = TextUtilities.getAlphabet(text);
        byteTextData = TextUtilities.convertToByteArray(text, this.alphabet);
        byteTextDataObject = new Byte[byteTextData.length];
        for (int i = 0; i < byteTextData.length; i++){
            byteTextDataObject[i] = byteTextData[i];
        }
    }
    public void setBytes(byte[] text, Alphabet alphabet){
        byteTextData = text;
        byteTextDataObject = new Byte[byteTextData.length];
        for (int i = 0; i < byteTextData.length; i++){
            byteTextDataObject[i] = byteTextData[i];
        }
        stringTextData = TextUtilities.convertToString(byteTextData, alphabet);
        this.alphabet = alphabet;
    }
    public void setBytesObject(Byte[] text, Alphabet alphabet){
        byteTextDataObject = text;
        byteTextData = new byte[byteTextDataObject.length];
        for (int i = 0; i < byteTextDataObject.length; i++){
            byteTextData[i] = byteTextDataObject[i];
        }
        stringTextData = TextUtilities.convertToString(byteTextData, alphabet);
        this.alphabet = alphabet;
    }
    public void toUpperCase(){
        setString(stringTextData.toUpperCase());
    }
    public void toLowerCase(){
        setString(stringTextData.toLowerCase());
    }

    public Alphabet getAlphabet(){
        return alphabet;
    }

    public void setAlphabet(Alphabet alphabet){
        this.alphabet = alphabet;
        TextUtilities.filterText(stringTextData, alphabet);
    }
}
