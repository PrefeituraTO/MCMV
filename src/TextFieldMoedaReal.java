import java.awt.event.*;
import java.math.*;
import javax.swing.*;

public class TextFieldMoedaReal extends JTextField implements KeyListener {
	private StringBuilder lastValidNumber;
	private int maxDigits=10;


	/**
	 * Aceita um BigDecimal com escala 2.
	 * Outras escalas não são serão aceitas
	 *
	 * @param BigDecimal
	 */

	public void setNumber(BigDecimal decimal){
		if(decimal.scale()==2&&decimal.unscaledValue().toString().length()<maxDigits){
			lastValidNumber=new StringBuilder(decimal.unscaledValue().toString());
			super.setText(getRealFormat());
		}
	}
	/**Retorna um BigDecimal com o valor encontrando no campo
	 * e com escala igual a 2
	 *
	 * @return BigDecimal
	 */
	public BigDecimal getNumber(){
		BigDecimal number;
		if(lastValidNumber.length()==0)
			number=new BigDecimal("0");
		else
			number=new BigDecimal(lastValidNumber.toString());
		number.setScale(2);
		return number.divide(new BigDecimal(100));
	}

	/**Aceita valor em centavos.
	 * O valor não deve possuir ponto ou vírgula
	 *
	 * @param String
	 */
	public void setText(String number){
		if(isNumber(number)&&number.length()<maxDigits)
			lastValidNumber=new StringBuilder(number);
		else if("".equals(number))
			lastValidNumber=new StringBuilder();
		super.setText(getRealFormat());

	}

	private boolean isNumber(String number){
		for(char c: number.toCharArray()){
			if(!Character.isDigit(c))
				return false;
		}
		return true;
	}
	public TextFieldMoedaReal() {
		super();
		this.setCaretPosition(this.getText().length());
		this.addKeyListener(this);
		lastValidNumber=new StringBuilder();
		super.setText(getRealFormat());
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		if(Character.isDigit(e.getKeyChar())&&lastValidNumber.length()<maxDigits){
			lastValidNumber.append(e.getKeyChar());
		}
		else if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE||e.getKeyCode()==KeyEvent.VK_DELETE){
			if(lastValidNumber.length()>0){
				lastValidNumber.deleteCharAt(lastValidNumber.length()-1);
			}
		}

		super.setText(getRealFormat());

	}

	public void keyTyped(KeyEvent e) {

	}

	private String getRealFormat(){
		if(lastValidNumber.length()==0)
			return "0,00";
		else if(lastValidNumber.length()==1)
			return "0,0"+lastValidNumber;
		else if(lastValidNumber.length()==2)
			return "0,"+lastValidNumber;
		else
			return buildPrefixSeparatedWithDots()+lastValidNumber.substring(lastValidNumber.length()-2);
	}

	private String buildPrefixSeparatedWithDots(){
		StringBuilder builder=new StringBuilder();
		for(int index=0;index<lastValidNumber.length()-2;++index){
			builder.append(lastValidNumber.charAt(index));
			if((lastValidNumber.length()-index)%3==0&&lastValidNumber.length()-index>5)
				builder.append(".");
		}
		builder.append(',');
		return builder.toString();
	}
	public int getMaxDigits() {
		return maxDigits;
	}
	public void setMaxDigits(int maxDigits) {
		this.maxDigits = maxDigits;
	}

}