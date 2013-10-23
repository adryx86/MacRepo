package it.ade;

public class loadWS 
{
	
	public static void main (String [] args)
	{
	/**
	// Definisco quanti elementi mi ritorna il webservice
	int[] numeratore = returnElements();
	
	// Creo una matrice con i dati recuperati dal webservice
	String[][] elenco = returnDataArray(numeratore[0],numeratore[1]);
	
	for (int i = 0; i< numeratore[0]; i++)
	{
		for (int k = 0; k< numeratore[1]; k++)
		{
			System.out.println("Valore : " + elenco[i][k]);
		}
	}
	**/

	}

	/**
	 * Metodo utilizzato per l'elaborazione dei dati prelevati dal webservice
	 * ed alla valorizzazione di una matrice di x,y elementi
	 * @param len_01
	 * @param len_02
	 * @return
	 */
	public String[][] returnDataArray(int len_01, int len_02)
	{
		String[][] arr = new String[len_01][len_02];
		
		for (int i = 0; i< len_01; i++)
		{
			for (int k = 0; k< len_02; k++)
			{
				arr[i][k] = "testo_"+i+"_"+k;
			}
		}
		
		return arr;
	}
	
	
	/**
	 * Metodo utilizzato per il richiamo al webservice per definire quanti elementi mi ritorna
	 * il servizio, considerando che per me l'elenco dei medici è un array di x elementi
	 * ed ogni medico è, a sua volta, un array di y elementi
	 * @return
	 */
	public int[] returnElements()
	{
		int[] arr = new int[2];
		
		arr[0] = 10;
		arr[1] = 5;
		
		return arr;
	}

}
