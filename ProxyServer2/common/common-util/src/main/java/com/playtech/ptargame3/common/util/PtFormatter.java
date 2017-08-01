package com.playtech.ptargame3.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Java logger formatter, that is as close as possible to log4 output, that is used in playtech.
 *
 * @author janno
 */
public class PtFormatter extends Formatter {

    private SimpleDateFormat dateFormat = new SimpleDateFormat( "MMdd-HHmm:ss,SSS" );

    @Override
    public String format( LogRecord record ) {
        StringBuilder str = new StringBuilder( 1000 );
        str.append( formatLevel( record.getLevel() ) );
        str.append( " " ).append( formatDate( record.getMillis() ) );
        str.append( " " ).append( record.getMessage() );
        if ( record.getParameters() != null ) {
            Object params[] = record.getParameters();
            if ( params.length > 0 ) {
                str.append( "{ " ).append( params[0] );
            }
            for ( int i = 1; i < params.length; ++i ) {
                str.append( ", " ).append( params[i] );
            }
            if ( params.length > 0 ) {
                str.append( " }" );
            } else {
                str.append( "{}" );
            }
        }
        str.append( " [" ).append( Thread.currentThread().getName() ).append( "]" );
        if ( record.getThrown() != null ) {
            str.append( "\r\n" );
            printStackTrace( str, record.getThrown(), " [" + Thread.currentThread().getName() + "]" );
        }
        str.append( "\r\n" );
        return str.toString();
    }

    private String formatLevel( Level level ) {
        if ( level.intValue() < Level.INFO.intValue() ) {
            return "D";
        } else if ( level.intValue() < Level.WARNING.intValue() ) {
            return "I";
        } else if ( level.intValue() < Level.SEVERE.intValue() ) {
            return "W";
        } else {
            return "E";
        }
    }

    private String formatDate( long millis ) {
        synchronized ( dateFormat ) {
            return dateFormat.format( new Date( millis ) );
        }
    }

    private void printStackTrace( StringBuilder str, Throwable t, String suffix ) {
        str.append( t.toString() ).append( suffix ).append( "\r\n" );
        StackTraceElement[] trace = t.getStackTrace();
        for ( int i = 0; i < trace.length; i++ ){
            str.append( "\tat " ).append( trace[i] ).append( suffix ).append( "\r\n" );
        }
        Throwable ourCause = t.getCause();
        if ( ourCause != null ) {
            printStackTraceAsCause( str, trace, ourCause, suffix );
        }
    }

    private void printStackTraceAsCause( StringBuilder str, StackTraceElement[] causedTrace, Throwable cause, String suffix ) {
        // Compute number of frames in common between this and caused
        StackTraceElement[] trace = cause.getStackTrace();
        int m = trace.length-1, n = causedTrace.length-1;
        while (m >= 0 && n >=0 && trace[m].equals(causedTrace[n])) {
            m--; n--;
        }
        int framesInCommon = trace.length - 1 - m;

        str.append( "Caused by: " ).append( cause ).append( suffix ).append( "\r\n" );
        for (int i=0; i <= m; i++){
            str.append( "\tat " ).append( trace[i] ).append( suffix ).append( "\r\n" );
        }
        if (framesInCommon != 0){
            str.append( "\t... " ).append( framesInCommon ).append( " more" ).append( suffix ).append( "\r\n" );
        }
        // Recurse if we have a cause
        Throwable ourCause = cause.getCause();
        if ( ourCause != null ){
            printStackTraceAsCause( str, trace, ourCause, suffix );
        }
    }

}

