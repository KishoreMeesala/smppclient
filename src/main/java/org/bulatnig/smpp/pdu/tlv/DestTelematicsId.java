package org.bulatnig.smpp.pdu.tlv;

import org.bulatnig.smpp.util.SMPPByteBuffer;
import org.bulatnig.smpp.util.WrongLengthException;
import org.bulatnig.smpp.util.WrongParameterException;
import org.bulatnig.smpp.pdu.EsmClass;

/**
 * This parameter defines the telematic interworking to be used by the
 * delivering system for the destination address. This is only useful when a
 * specific dest_bearer_type parameter has also been specified as the value is
 * bearer dependent. In the case that the receiving system (e.g. SMSC) does not
 * support the indicated telematic interworking, it may treat this a failure and
 * return a response PDU reporting a failure.
 *
 * @author Bulat Nigmatullin
 */
public class DestTelematicsId extends TLV {
    /**
     * Длина значения параметра.
     */
    private static final int LENGTH = 2;
    /**
     * Значение параметра.
     */
    private int value;

    /**
     * Constructor.
     *
     * @param telematicsId значение параметра
     * @throws TLVException ошибка разбора TLV
     */
    public DestTelematicsId(final int telematicsId) throws TLVException {
        super(ParameterTag.DEST_TELEMATICS_ID);
        value = telematicsId;
    }

    /**
     * Constructor.
     *
     * @param bytes bytecode of TLV
     * @throws TLVException ошибка разбора TLV
     */
    public DestTelematicsId(final byte[] bytes) throws TLVException {
        super(bytes);
    }

    @Override
    protected void parseValue(byte[] bytes, final EsmClass esmClass, final short dataCoding) throws TLVException {
        if (getTag() != ParameterTag.DEST_TELEMATICS_ID) {
            throw new ClassCastException();
        }
        if (bytes.length == LENGTH) {
            try {
                value = new SMPPByteBuffer(bytes).removeShort();
            } catch (WrongLengthException e) {
                throw new TLVException("Buffer error during parsing value", e);
            }
        } else {
            throw new TLVException("Value has wrong length: " + bytes.length + " but expected " + LENGTH);
        }
    }

    @Override
    protected byte[] getValueBytes(final EsmClass esmClass, final short dataCoding) throws TLVException {
        SMPPByteBuffer sbb = new SMPPByteBuffer();
        try {
            sbb.appendShort(value);
        } catch (WrongParameterException e) {
            throw new TLVException("Buffer error during parsing value", e);
        }
        return sbb.getBuffer();
    }

    /**
     * @return значение параметра
     */
    public final int getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return getClass().getName() + " Object {" + "\nvalue : " + value
				+ "\n}";
	}

}