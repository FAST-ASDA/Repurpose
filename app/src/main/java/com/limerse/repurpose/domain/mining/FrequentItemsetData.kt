
package com.limerse.repurpose.domain.mining

/**
 * This class holds the result information of a data-mining task.
 *
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Sep 14, 2015)
 */
class FrequentItemsetData<I> internal constructor(
    val frequentItemsetList: List<Set<I>>,
    val supportCountMap: Map<Set<I>, Int>,
    val minimumSupport: Double,
    val transactionNumber: Int
) {
    fun getSupport(itemset: Set<I>): Double {
        return 1.0 * supportCountMap[itemset]!! / transactionNumber
    }
}