package edu.ucsd.genome.chromviz.testExamples;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import keggapi.KEGGLocator;
import keggapi.KEGGPortType;

public class KeggApiTest {

	/**
	 * @param args
	 * @throws ServiceException 
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws ServiceException, RemoteException {
		KEGGLocator locator = new KEGGLocator();
		KEGGPortType serv = locator.getKEGGPort();		
		String tit = serv.btit("mmu:11920");
		//String tit = serv.bget("-f -n n mmu:11920");
		System.out.println(tit);
	}

}
