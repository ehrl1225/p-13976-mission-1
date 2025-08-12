package global.expect

class Expected(
    val expectedOutputs:List<String>,
    val actualOutputs:List<String>,
) {

    /**
     * Checks if the actual outputs contain the expected outputs in order.
     */
    fun check(){
        var expectedIndex = 0
        var actualIndex = 0

        while (expectedIndex < expectedOutputs.size && actualIndex < actualOutputs.size){
            val expected = expectedOutputs.getOrNull(expectedIndex)
            val actual = actualOutputs.getOrNull(actualIndex)
            if (expected == null) {
                throw AssertionError("Expected output is null at index $expectedIndex, but actual output is '$actual'")
            }
            if (actual == null) {
                throw AssertionError("Actual output is null at index $actualIndex, but expected output is '$expected'")
            }
            if (actual.contains(expected)){
                expectedIndex++
                actualIndex++
            }else{
                actualIndex++
            }
        }
        if (expectedIndex < expectedOutputs.size) {
            throw AssertionError("Expected outputs not fully matched. Remaining expected outputs: ${expectedOutputs.subList(expectedIndex, expectedOutputs.size)}")
        }

    }
}