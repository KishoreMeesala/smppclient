package org.bulatnig.smpp.pdu.tlv;

import org.bulatnig.smpp.util.SMPPByteBuffer;
import org.bulatnig.smpp.util.WrongLengthException;
import org.bulatnig.smpp.util.WrongParameterException;
import org.bulatnig.smpp.pdu.MessageState;
import org.bulatnig.smpp.pdu.EsmClass;

/**
 * The message_state optional parameter is used by the SMSC in the deliver_sm
 * and data_sm PDUs to indicate to the ESME the final message state for an SMSC
 * Delivery Receipt.
 *
 * @author Bulat Nigmatullin
 */
public class MessageStateTlv extends TLV {
    /**
     * Длина значения параметра.
     */
    private static final int LENGTH = 1;
    /**
     * Значение параметра.
     */
    private MessageState value;

    /**
     * Constructor.
     *
     * @param state значение параметра
     */
    public MessageStateTlv(final MessageState state) {
        super(ParameterTag.MESSAGE_STATE);
        value = state;
    }

    /**
     * Constructor.
     *
     * @param bytes bytecode of TLV
     * @throws TLVException ошибка разбора TLV
     */
    public MessageStateTlv(final byte[] bytes) throws TLVException {
        super(bytes);
    }

    @Override
    protected void parseValue(byte[] bytes, final EsmClass esmClass, final short dataCoding) throws TLVException {
        if (getTag() != ParameterTag.MESSAGE_STATE) {
            throw new ClassCastException();
        }
        if (bytes.length == LENGTH) {
            short b;
            try {
                b = new SMPPByteBuffer(bytes).removeByte();
            } catch (WrongLengthException e) {
                throw new TLVException("Buffer error during parsing value", e);
            }
            for (MessageState state : MessageState.values()) {
                if (state.getValue() == b) {
                    value = state;
                }
            }
            if (value == null) {
                throw new TLVException("Corresponding TLV value not found: " + b);
            }
        } else {
            throw new TLVException("Value has wrong length: " + bytes.length + " but expected " + LENGTH);
        }
    }

    @Override
    protected byte[] getValueBytes(final EsmClass esmClass, final short dataCoding) throws TLVException {
        SMPPByteBuffer sbb = new SMPPByteBuffer();
        try {
            sbb.appendByte(value.getValue());
        } catch (WrongParameterException e) {
            throw new TLVException("Buffer error during parsing value", e);
        }
        return sbb.getBuffer();
    }

    /**
     * @return значение параметра
     */
    public final MessageState getValue() {
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