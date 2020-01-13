package nl.unimaas.ids;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

/**
 * A class to upload RDF to a RDF4J repository
 */
public class SparqlLoader {	

	public static void uploadRdf(String filePath, Repository repo, String graphUri) throws Exception {
		System.out.println("before getconnection");
//		try (RepositoryConnection conn = repo.getConnection()) {
		RepositoryConnection conn = repo.getConnection();
		ValueFactory vf = SimpleValueFactory.getInstance();
		System.out.println("before filepath");
		File inputFile = new File(filePath);
		if(!inputFile.exists())
			throw new IllegalArgumentException("Input file \"" + inputFile.getAbsolutePath() + "\" does not exist");
		if(!inputFile.canRead())
			throw new SecurityException("Can not read from input file \"" + inputFile.getAbsolutePath() + "\"");

		if (inputFile.isDirectory()) {
			System.out.println("before file filter ");
			// If the provided input path is a directory, then we iterate over files if this dir
			Collection<File> files = FileUtils.listFiles(
					inputFile,
					new RegexFileFilter(".*\\.(nt|nq|n3|ttl|rdf)(\\.gz)*"),
					DirectoryFileFilter.DIRECTORY
			);
			System.out.println("before iterator directory: ");
			System.out.println(files.size());
			System.out.println(files.toString());
			// Recursively iterate over files in the directory
			Iterator<File> iterator = files.iterator();
			while (iterator.hasNext()) {
				File f = iterator.next();
				System.out.println("Uploading: " + f);
				if (graphUri != null) {
					conn.add(f, null, Rio.getParserFormatForFileName(f.getName()).get(), vf.createIRI(graphUri));
				} else {
					conn.add(f, null, Rio.getParserFormatForFileName(f.getName()).get());
				}
			}
		} else {
			// If the provided input path is a file we process it
			if (graphUri != null) {
				conn.add(new File(filePath), null, Rio.getParserFormatForFileName(inputFile.getName()).get(), vf.createIRI(graphUri));
			} else {
				conn.add(new File(filePath), null, Rio.getParserFormatForFileName(inputFile.getName()).get());
			}
		}
//		} catch (Exception e) { throw e; }

		repo.shutDown();
	}
}