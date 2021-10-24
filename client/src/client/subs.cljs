(ns client.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::query
 (fn [db [_ query]]
   (get db query)))
