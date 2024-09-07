package com.core.ciphers

import com.core.alphabets.UppercaseLetters
import com.core.keys.KeyFactory
import com.core.cipherdata.CipherDataBlock
import com.core.collections.BiMap

class FourSquareCipherTests extends munit.FunSuite {
    test("FourSquareCipher.encrypt") {
        val plaintext = "BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIRTEENWINSTONSMITHHISCHINNUZZLEDINTOHISBREASTINANEFFORTTOESCAPETHEVILEWINDSLIPPEDQUICKLYTHROUGHTHEGLASSDOORSOFVICTORYMANSIONSTHOUGHNOTQUICKLYENOUGHTOPREVENTASWIRLOFGRITTYDUSTFROMENTERINGALONGWITHHIMTHEHALLWAYSMELTOFBOILEDCABBAGEANDOLDRAGMATSATONEENDOFITACOLOUREDPOSTERTOOLARGEFORINDOORDISPLAYHADBEENTACKEDTOTHEWALLITDEPICTEDSIMPLYANENORMOUSFACEMORETHANAMETREWIDETHEFACEOFAMANOFABOUTFORTYFIVEWITHAHEAVYBLACKMOUSTACHEANDRUGGEDLYHANDSOMEFEATURESWINSTONMADEFORTHESTAIRSITWASNOUSETRYINGTHELIFTEVENATTHEBESTOFTIMESITWASSELDOMWORKINGANDATPRESENTTHEELECTRICCURRENTWASCUTOFFDURINGDAYLIGHTHOURSITWASPARTOFTHEECONOMYDRIVEINPREPARATIONFORHATEWEEKTHEFLATWASSEVENFLIGHTSUPANDWINSTONWHOWASTHIRTYNINEANDHADAVARICOSEULCERABOVEHI"
        val expected = "CFFRDTFRDTZODLGHPWOTRKSYWZLHQKBRTGDMEBRRHPWRQKWEKAPADDRSEKSXDQWWTTKIHPBZRIPWDOKYHPPXOTNSHVOFGVBOHPHGZZDBCLHYOAGVFPWDPXHPOUWKCTPWQTWWADMRPARWMILWHPAXRLGRLOPCMCMKVYDPOGBRPAOKWENXCSDTQSCYMCQTQOGEGYIPGYPAOGBRITSTQNEKRBOQOGBRQTMURWOQNGSXMPRSBMQKPYYANZNMQQMBHYDQHPAHRSHMXKPARIHXDFWCDLUDUYMBOXWMCPMILOADECONWDKADMFSONGENYOZOTDOKAWMHVDHDMOGTOABWYTAPWRTWEPKWKDTHPCSDTCLNBWEVALRFFOQNGEKLOQTPALWDNRLSAWOMCTAAXGPMNUGMAITNQOGNLDHWQDTOWWCGGMBTTLWKFOWDFAGFOWMWUOUWMLHOGNMDTQACMZFXKPAORWDXFFIDHGQOGPXDHDFOUFSPNFRFLVAOUAXWTWKWDPGTOSXHPPXOTGEFBCTPWDFPXRNNWHVUDPSOGTBTTXMHMPADKGITAZFGGPYDFFFPXWMQMMBQLSYWZTBLBWTXQSKHPAHKAOZMUWWOQPYDFDKDDTTMCBDTQOQSYWZBDQTAMBEQKHMAEZMHIOVRPTUQLSYWZGHPWWMPADOCQITIBFSIVRKGATOGHNDQMOTCTPOOZLWDOHWDFFMOZUDNXRWOQFMHIOVPEGHKAXKGYQTKYRPUDPXRIPWVTHPWDKAWCAEUCQKCQTBTNFONDCPZFRI"
        val dropJ = UppercaseLetters.dropLetter('J')

        val key1 = BiMap.createFromSeq(KeyFactory.combinePhraseWithAlphabet("HELLO", dropJ))
        val key2 = BiMap.createFromSeq(KeyFactory.combinePhraseWithAlphabet("WORLD", dropJ))
        val key3 = BiMap.createFromSeq(KeyFactory.combinePhraseWithAlphabet("FOOBAR", dropJ))
        val key4 = BiMap.createFromSeq(KeyFactory.combinePhraseWithAlphabet("PHYDEAUX", dropJ))
        val totalKey = Vector(key1, key2, key3, key4)

        val data = new CipherDataBlock(plaintext, UppercaseLetters)
        val result = FourSquareCipher.encrypt(data, totalKey).mkString

        assertEquals(result, expected)
    }
    test("FourSquareCipher.decrypt") {
        val plaintext = "CFFRDTFRDTZODLGHPWOTRKSYWZLHQKBRTGDMEBRRHPWRQKWEKAPADDRSEKSXDQWWTTKIHPBZRIPWDOKYHPPXOTNSHVOFGVBOHPHGZZDBCLHYOAGVFPWDPXHPOUWKCTPWQTWWADMRPARWMILWHPAXRLGRLOPCMCMKVYDPOGBRPAOKWENXCSDTQSCYMCQTQOGEGYIPGYPAOGBRITSTQNEKRBOQOGBRQTMURWOQNGSXMPRSBMQKPYYANZNMQQMBHYDQHPAHRSHMXKPARIHXDFWCDLUDUYMBOXWMCPMILOADECONWDKADMFSONGENYOZOTDOKAWMHVDHDMOGTOABWYTAPWRTWEPKWKDTHPCSDTCLNBWEVALRFFOQNGEKLOQTPALWDNRLSAWOMCTAAXGPMNUGMAITNQOGNLDHWQDTOWWCGGMBTTLWKFOWDFAGFOWMWUOUWMLHOGNMDTQACMZFXKPAORWDXFFIDHGQOGPXDHDFOUFSPNFRFLVAOUAXWTWKWDPGTOSXHPPXOTGEFBCTPWDFPXRNNWHVUDPSOGTBTTXMHMPADKGITAZFGGPYDFFFPXWMQMMBQLSYWZTBLBWTXQSKHPAHKAOZMUWWOQPYDFDKDDTTMCBDTQOQSYWZBDQTAMBEQKHMAEZMHIOVRPTUQLSYWZGHPWWMPADOCQITIBFSIVRKGATOGHNDQMOTCTPOOZLWDOHWDFFMOZUDNXRWOQFMHIOVPEGHKAXKGYQTKYRPUDPXRIPWVTHPWDKAWCAEUCQKCQTBTNFONDCPZFRI"
        val expected = "BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIRTEENWINSTONSMITHHISCHINNUZZLEDINTOHISBREASTINANEFFORTTOESCAPETHEVILEWINDSLIPPEDQUICKLYTHROUGHTHEGLASSDOORSOFVICTORYMANSIONSTHOUGHNOTQUICKLYENOUGHTOPREVENTASWIRLOFGRITTYDUSTFROMENTERINGALONGWITHHIMTHEHALLWAYSMELTOFBOILEDCABBAGEANDOLDRAGMATSATONEENDOFITACOLOUREDPOSTERTOOLARGEFORINDOORDISPLAYHADBEENTACKEDTOTHEWALLITDEPICTEDSIMPLYANENORMOUSFACEMORETHANAMETREWIDETHEFACEOFAMANOFABOUTFORTYFIVEWITHAHEAVYBLACKMOUSTACHEANDRUGGEDLYHANDSOMEFEATURESWINSTONMADEFORTHESTAIRSITWASNOUSETRYINGTHELIFTEVENATTHEBESTOFTIMESITWASSELDOMWORKINGANDATPRESENTTHEELECTRICCURRENTWASCUTOFFDURINGDAYLIGHTHOURSITWASPARTOFTHEECONOMYDRIVEINPREPARATIONFORHATEWEEKTHEFLATWASSEVENFLIGHTSUPANDWINSTONWHOWASTHIRTYNINEANDHADAVARICOSEULCERABOVEHI"
        val dropJ = UppercaseLetters.dropLetter('J')

        val key1 = BiMap.createFromSeq(KeyFactory.combinePhraseWithAlphabet("HELLO", dropJ))
        val key2 = BiMap.createFromSeq(KeyFactory.combinePhraseWithAlphabet("WORLD", dropJ))
        val key3 = BiMap.createFromSeq(KeyFactory.combinePhraseWithAlphabet("FOOBAR", dropJ))
        val key4 = BiMap.createFromSeq(KeyFactory.combinePhraseWithAlphabet("PHYDEAUX", dropJ))
        val totalKey = Vector(key1, key2, key3, key4)

        val data = new CipherDataBlock(plaintext, UppercaseLetters)
        val result = FourSquareCipher.decrypt(data, totalKey).mkString

        assertEquals(result, expected)
    }
}
