(ns client.views
  (:require
   [re-frame.core :as re-frame]
   [client.events :as events]
   [client.subs :as subs]))

(defn main-panel []
  (let [str1 @(re-frame/subscribe [::subs/query :str1])
        str2 @(re-frame/subscribe [::subs/query :str2])
        scramble? @(re-frame/subscribe [::subs/query :scramble-text])
        color-result @(re-frame/subscribe [::subs/query :scramble-color])]
    [:div {:style {:height "100vh" :width "100%" :color "white"}}
     [:h1 {:style {:text-align :center}}
      "Scramble"]
     [:div {:style {:padding-left "30%"}}
      [:h2 "String 1:" [:input {:type :text
                                :value str1
                                :on-change #(re-frame/dispatch [::events/change-str [:str1 (-> % .-target .-value)]])
                                :style {:margin-left "20px"}}]]
      [:h2 "String 2:" [:input {:type :text
                                :value str2
                                :on-change #(re-frame/dispatch [::events/change-str [:str2 (-> % .-target .-value)]])
                                :style {:margin-left "20px"}}]]
      [:button  {:on-click #(re-frame/dispatch [:http-request-handler {:str1 str1 :str2 str2}])} "Scramble!"]
      [:h3 "Result:" [:span {:style {:margin-left "15px" :color color-result}}  scramble?]]]]))
