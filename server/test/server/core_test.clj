(ns server.core-test
  (:require [clojure.test :refer :all]
            [server.core :refer :all]))

(deftest scramble-test
  (testing "[OK] Testing scramble with inputs that can be scrambled. Should return true."
    (is (= (scramble "rekqodlw" "world") true))
    (is (= (scramble "cedewaraaossoqqyt" "codewars") true)))
  (testing "[OK] Testing scramble with inputs that can not be scrambled. Should return false."
    (is (= (scramble "katas" "steak") false))
    (is (= (scramble "abcd" "bcde") false)))
  (testing "[ERROR] Inputs should not contain numbers or special characters."
    (is (= (scramble "abc0" "1aas") {:error "Invalid input. Expected only a-z lower characters."}))
    (is (= (scramble ";a" "..b,") {:error "Invalid input. Expected only a-z lower characters."}))
    (is (= (scramble "#!@s@$baa" "l´~][!]") {:error "Invalid input. Expected only a-z lower characters."})))
  (testing "[ERROR] Even though the inputs could be scrambled, only lower case inputs should be accepted."
    (is (= (scramble "REKQODLW" "WORLD") {:error "Invalid input. Expected only a-z lower characters."}))
    (is (= (scramble "CedewaraaosSoqqyT" "codewars") {:error "Invalid input. Expected only a-z lower characters."}))))
