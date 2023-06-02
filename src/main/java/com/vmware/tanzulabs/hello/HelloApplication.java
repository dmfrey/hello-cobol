package com.vmware.tanzulabs.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sun.jna.*;

@SpringBootApplication
@RestController
public class HelloApplication {
	
	private static final Logger log = LoggerFactory.getLogger( HelloApplication.class );

	// static {

	// 	System.loadLibrary( "Hello" );

	// }
	// public native void printHello();

	public static void main( String[] args ) {

		/*
         * try and initialise the GnuCOBOL run time
         * calling cob_init with no parameters
		 */
        try {
			
			libcob.INSTANCE.cob_init( 0, null );
		
		} catch (UnsatisfiedLinkError e) {
			log.error( "Libcob Exception", e );
		}

		SpringApplication.run( HelloApplication.class, args );

	}

	@GetMapping( value="/" )
	public void callPrintHello() {
		log.info( "callPrintHello : enter" );

		log.info( "callPrintHello : calling native code" );
		hello.INSTANCE.printhello();
	
	}
	
}

/*
 * libcob interface, initialising GnuCOBOL run time
 */
interface libcob extends Library {
	libcob INSTANCE = (libcob) Native.loadLibrary( "cob", libcob.class );
	void cob_init( int argc, Pointer argv );
}

/*
 * hello COBOL program interface, single program
 */
interface hello extends Library {
	hello INSTANCE = (hello) Native.loadLibrary( "Hello", hello.class );
	void printhello();
}
