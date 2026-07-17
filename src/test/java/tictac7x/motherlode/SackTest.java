package tictac7x.motherlode;

import net.runelite.api.*;
import net.runelite.api.gameval.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.junit.*;
import java.util.*;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SackTest {
    @Mock
    Client client;

    Provider provider;
    Sack sack;

    @Before
    public void setup() {
        provider = new Provider(client);
        sack = new Sack(provider);
    }

    @Test
    public void PaydirtAmount() {
        when(client.getVarbitValue(VarbitID.MOTHERLODE_SACK_TRANSMIT)).thenReturn(0);
        when(client.getVarbitValue(VarbitID.MOTHERLODE_BIGGERSACK)).thenReturn(0);
        assertEquals(0, sack.getPaydirt());
        assertEquals(108, sack.getSpaceLeft());

        when(client.getVarbitValue(VarbitID.MOTHERLODE_SACK_TRANSMIT)).thenReturn(10);
        assertEquals(10, sack.getPaydirt());
        assertEquals(98, sack.getSpaceLeft());

        when(client.getVarbitValue(VarbitID.MOTHERLODE_SACK_TRANSMIT)).thenReturn(108);
        assertEquals(108, sack.getPaydirt());
        assertEquals(0, sack.getSpaceLeft());

        when(client.getVarbitValue(VarbitID.MOTHERLODE_BIGGERSACK)).thenReturn(1);
        when(client.getVarbitValue(VarbitID.MOTHERLODE_SACK_TRANSMIT)).thenReturn(189);
        assertEquals(189, sack.getPaydirt());
        assertEquals(0, sack.getSpaceLeft());

        when(client.getVarbitValue(VarbitID.MOTHERLODE_SACK_TRANSMIT)).thenReturn(0);
        assertEquals(0, sack.getPaydirt());
        assertEquals(189, sack.getSpaceLeft());

        when(client.getVarbitValue(VarbitID.MOTHERLODE_SACK_TRANSMIT)).thenReturn(20);
        assertEquals(169, sack.getSpaceLeft());
    }

    @Test
    public void Sizes() {
        when(client.getVarbitValue(VarbitID.MOTHERLODE_BIGGERSACK)).thenReturn(0);
        assertEquals(108, sack.getSize());

        when(client.getVarbitValue(VarbitID.MOTHERLODE_BIGGERSACK)).thenReturn(1);
        assertEquals(189, sack.getSize());
    }
}
