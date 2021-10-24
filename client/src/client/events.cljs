(ns client.events
  (:require
   [re-frame.core :as rf]
   [ajax.core :as ajax]
   [day8.re-frame.http-fx]
   [client.db :as db]))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(rf/reg-event-db
 ::change-str
 (fn [db [_ event]]
   (let [path (nth event 0)
         value (nth event 1)]
     (assoc db path value))))

(rf/reg-event-db
 :http-response-handler
 (fn [db [_ response]]
   (let [scramble? (:scramble response)
         value (cond
                 (true? scramble?) {:text "Can be scrambled." :color "green"}
                 (false? scramble?) {:text "Can not be scrambled." :color "yellow"}
                 :else {:text (get-in response [:response :scramble :error]) :color "red"})]
     (-> db
         (assoc :scramble-text (:text value))
         (assoc :scramble-color (:color value))))))

(rf/reg-event-fx                        
 :http-request-handler                      
 (fn [{:keys [db]} [_ request]]
   {:db   db
    :http-xhrio {:method          :post
                 :uri             "http://host.docker.internal:3333"
                 :params          request
                 :timeout         5000
                 :format          (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [:http-response-handler]
                 :on-failure      [:http-response-handler]}}))