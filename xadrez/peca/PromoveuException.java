/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 05/04/2014
 */
package xadrez.peca;

public class PromoveuException extends Exception {
	String oi;
	public PromoveuException () {
		oi = "promoveu, UHUL!";
	}
}