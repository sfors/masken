(ns masken.main
  (:require [rum.core :as rum]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [masken.view.app-component :refer [app-component]]))

(defonce state-atom (atom {:title "Masken!"}))


(defn button-clicked-handler
  [{db :db} [_ id]]
  {:db (update-in db [id :counter] inc)})


(defn query-counter
  [db [_ id]]
  (get-in db [id :counter]))


(re-frame/reg-event-fx
  :button-clicked
  button-clicked-handler)


(re-frame/reg-event-fx
  :tick
  (fn [{db :db} _]
    {:db (update db :time inc)}))


(re-frame/reg-sub
  :query-counter
  query-counter)

(re-frame/reg-sub
  :query-time
  (fn [db _]
    (:time db)))

(re-frame/reg-event-db
  :initialize
  (fn [_ _]
    {:time 0
     "1" {:counter 0}
     "2" {:counter 0}}))


(defn render
  []
  (reagent/render [app-component] (js/document.getElementById "app")))

(defn on-reload
  {:dev/after-load true}
  []
  (render))


(defn main!
  []
  (re-frame/dispatch [:initialize])
  (render)
  (js/setInterval (fn [] (re-frame/dispatch [:tick])) 1000))
