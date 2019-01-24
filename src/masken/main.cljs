(ns masken.main
  (:require [rum.core :as rum]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [masken.view.app-component :refer [app-component]]))

(defonce state-atom (atom {:title "Masken!"}))


(defn button-clicked-handler
  [{db :db} [_ id]]
  {:db (if (get-in db [id :counter])
         (update-in db [id :counter] inc)
         (assoc-in db [id :counter] 1))})


(defn query-counter
  [db [_ id]]
  (get-in db [id :counter]))


(re-frame/reg-event-fx
  :button-clicked
  button-clicked-handler)


(re-frame/reg-sub
  :query-counter
  query-counter)


(defn render
  []
  (reagent/render [app-component] (js/document.getElementById "app")))

(defn on-reload
  {:dev/after-load true}
  []
  (render))


(defn main!
  []
  (render))
