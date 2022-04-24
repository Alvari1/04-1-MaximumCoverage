import org.junit.Test
import kotlin.test.assertEquals

class MainKtTest {

    @Test
    fun `calculateCommission MASTERCARD with monthTransferAmount in non commission range`() {
        val cardType = CardTypes.MASTERCARD
        val amount = 0U
        val monthTransferAmount =
            MASTERCARD_MAESTRO_NO_COMMISSION_MAX_PER_MONTH - MASTERCARD_MAESTRO_NO_COMMISSION_MIN_PER_MONTH
        val expectedResult = 0U

        val result =
            calculateCommission(cardType = cardType, monthTransferAmount = monthTransferAmount, amount = amount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `calculateCommission MASTERCARD with monthTransferAmount exceed commission range min`() {
        val cardType = CardTypes.MASTERCARD
        val amount = 0U
        val monthTransferAmount =
            MASTERCARD_MAESTRO_NO_COMMISSION_MIN_PER_MONTH / 2U
        val expectedResult = COMMISSION_MASTERCARD_MAESTRO_ADD

        val result =
            calculateCommission(cardType = cardType, monthTransferAmount = monthTransferAmount, amount = amount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `calculateCommission MASTERCARD with monthTransferAmount exceed commission range max`() {
        val cardType = CardTypes.MASTERCARD
        val amount = 0U
        val monthTransferAmount =
            MASTERCARD_MAESTRO_NO_COMMISSION_MAX_PER_MONTH + 1U
        val expectedResult = COMMISSION_MASTERCARD_MAESTRO_ADD

        val result =
            calculateCommission(cardType = cardType, monthTransferAmount = monthTransferAmount, amount = amount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `calculateCommission MAESTRO with monthTransferAmount in non commission range`() {
        val cardType = CardTypes.MAESTRO
        val amount = MASTERCARD_MAESTRO_NO_COMMISSION_MIN_PER_MONTH / 2U
        val monthTransferAmount =
            MASTERCARD_MAESTRO_NO_COMMISSION_MAX_PER_MONTH - MASTERCARD_MAESTRO_NO_COMMISSION_MIN_PER_MONTH
        val expectedResult = 0U

        val result =
            calculateCommission(cardType = cardType, monthTransferAmount = monthTransferAmount, amount = amount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `calculateCommission MAESTRO with monthTransferAmount exceed commission range min`() {
        val cardType = CardTypes.MAESTRO
        val amount = 0U
        val monthTransferAmount =
            MASTERCARD_MAESTRO_NO_COMMISSION_MIN_PER_MONTH / 2U
        val expectedResult = COMMISSION_MASTERCARD_MAESTRO_ADD

        val result =
            calculateCommission(cardType = cardType, monthTransferAmount = monthTransferAmount, amount = amount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `calculateCommission MAESTRO with monthTransferAmount exceed commission range max`() {
        val cardType = CardTypes.MAESTRO
        val amount = 0U
        val monthTransferAmount =
            MASTERCARD_MAESTRO_NO_COMMISSION_MAX_PER_MONTH + 1U
        val expectedResult = COMMISSION_MASTERCARD_MAESTRO_ADD

        val result =
            calculateCommission(cardType = cardType, monthTransferAmount = monthTransferAmount, amount = amount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `calculateCommission VISA with commission lower than minimum commission value`() {
        val cardType = CardTypes.VISA
        val amount = 0U
        val expectedResult = COMMISSION_VISA_MIR_MIN

        val result =
            calculateCommission(cardType = cardType, amount = amount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `calculateCommission VISA with commission higher than minimum commission value`() {
        val cardType = CardTypes.VISA
        val amount = 480000U
        val expectedResult = 3600U

        val result =
            calculateCommission(cardType = cardType, amount = amount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `calculateCommission MIR with commission lower than minimum commission value`() {
        val cardType = CardTypes.MIR
        val amount = 0U
        val expectedResult = COMMISSION_VISA_MIR_MIN

        val result =
            calculateCommission(cardType = cardType, amount = amount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `calculateCommission MIR with commission higher than minimum commission value`() {
        val cardType = CardTypes.MIR
        val amount = 480000U
        val expectedResult = 3600U

        val result =
            calculateCommission(cardType = cardType, amount = amount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `calculateCommission VKPay`() {
        val cardType = CardTypes.VKPAY
        val amount = 1U
        val expectedResult = 0U

        val result =
            calculateCommission(cardType = cardType, amount = amount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `calculateCommission with default values`() {
        val amount = 1U
        val expectedResult = 0U

        val result =
            calculateCommission(amount = amount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `checkLimits with default values`() {
        val amount = 0U
        val expectedResult = true

        val result = checkLimits(amount = amount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `checkLimits VKPay transfer amount in limit range`() {
        val cardType = CardTypes.VKPAY
        val amount = MAX_TRANSFER_FROM_VKPAY_ONCE
        val monthTransferAmount = 0U
        val expectedResult = true

        val result =
            checkLimits(cardType = cardType, amount = amount, monthTransferAmount = monthTransferAmount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `checkLimits VKPay transfer amount higher than limit at once`() {
        val cardType = CardTypes.VKPAY
        val amount = MAX_TRANSFER_FROM_VKPAY_ONCE + 1U
        val expectedResult = false

        val result =
            checkLimits(cardType = cardType, amount = amount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `checkLimits VKPay transfer amount higher than limit per month`() {
        val cardType = CardTypes.VKPAY
        val amount = 1U
        val monthTransferAmount = MAX_TRANSFER_FROM_VKPAY_PER_MONTH + amount
        val expectedResult = false

        val result =
            checkLimits(cardType = cardType, amount = amount, monthTransferAmount = monthTransferAmount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `checkLimits non VKPay limit in range`() {
        val cardType = CardTypes.MASTERCARD
        val amount = 0U
        val expectedResult = true

        val result =
            checkLimits(cardType = cardType, amount = amount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `checkLimits non VKPay transfer amount higher than limit per day`() {
        val cardType = CardTypes.MASTERCARD
        val amount = 1U
        val dailyTransferAmount = LIMIT_PER_DAY
        val expectedResult = false

        val result =
            checkLimits(cardType = cardType, amount = amount, dailyTransferAmount = dailyTransferAmount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `checkLimits non VKPay receive amount higher than limit per day`() {
        val cardType = CardTypes.MASTERCARD
        val amount = 1U
        val dailyRecieveAmount = LIMIT_PER_DAY
        val expectedResult = false

        val result =
            checkLimits(cardType = cardType, amount = amount, dailyRecieveAmount = dailyRecieveAmount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `checkLimits non VKPay transfer amount higher than limit per month`() {
        val cardType = CardTypes.MASTERCARD
        val amount = 1U
        val monthTransferAmount = LIMIT_PER_MONTH
        val expectedResult = false

        val result =
            checkLimits(cardType = cardType, amount = amount, monthTransferAmount = monthTransferAmount)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `checkLimits non VKPay receive amount higher than limit per month`() {
        val cardType = CardTypes.MASTERCARD
        val amount = 1U
        val monthRecieveAmount = LIMIT_PER_MONTH
        val expectedResult = true

        val result =
            checkLimits(cardType = cardType, amount = amount, monthRecieveAmount = monthRecieveAmount)
        assertEquals(result, expectedResult)
    }
}