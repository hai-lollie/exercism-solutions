(ns zebra-puzzle)

;; ── The domains of the puzzle ────────────────────────────────────────
;; Statement 1: there are five houses, and every attribute is distinct.
;; Using one permutation per domain makes both facts inherent.

(def nationalities [:englishman :spaniard :ukrainian :norwegian :japanese])
(def colors [:red :green :ivory :yellow :blue])
(def pets [:dog :snails :fox :horse :zebra])
(def drinks [:water :orange-juice :tea :milk :coffee])
(def hobbies [:dancing :painting :reading :football :chess])

;; ── Houses on the street ─────────────────────────────────────────────
;; A street is a map from each domain to its residents in house order:
;; {:nationalities [...] :colors [...] :pets [...] :drinks [...] :hobbies [...]}

(defn- permutations
  "All orderings of coll, each as a vector of five residents."
  [coll]
  (if (empty? coll)
    (list [])
    (for [who coll
          rest (permutations (remove #{who} coll))]
      (vec (cons who rest)))))

(defn- house-number
  "The number of the house where who lives among residents."
  [residents who]
  (some (fn [[house resident]]
          (when (= resident who) house))
        (map-indexed vector residents)))

(defn- lives-next-to?
  [house-a house-b]
  (= 1 (abs (- house-a house-b))))

(defn- lives-immediately-right-of?
  [house house-to-the-left]
  (= 1 (- house house-to-the-left)))

;; ── The fifteen statements ───────────────────────────────────────────

(defn- norwegian-lives-in-first-house?
  [{:keys [nationalities]}]
  (= :norwegian (first nationalities)))

(defn- englishman-lives-in-red-house?
  [{:keys [nationalities colors]}]
  (= :red (nth colors (house-number nationalities :englishman))))

(defn- spaniard-owns-the-dog?
  [{:keys [nationalities pets]}]
  (= :dog (nth pets (house-number nationalities :spaniard))))

(defn- green-house-resident-drinks-coffee?
  [{:keys [colors drinks]}]
  (= :coffee (nth drinks (house-number colors :green))))

(defn- ukrainian-drinks-tea?
  [{:keys [nationalities drinks]}]
  (= :tea (nth drinks (house-number nationalities :ukrainian))))

(defn- green-house-immediately-right-of-ivory-house?
  [{:keys [colors]}]
  (lives-immediately-right-of? (house-number colors :green)
                               (house-number colors :ivory)))

(defn- snail-owner-likes-to-go-dancing?
  [{:keys [pets hobbies]}]
  (= :dancing (nth hobbies (house-number pets :snails))))

(defn- yellow-house-resident-is-a-painter?
  [{:keys [colors hobbies]}]
  (= :painting (nth hobbies (house-number colors :yellow))))

(defn- middle-house-resident-drinks-milk?
  [{:keys [drinks]}]
  (= :milk (nth drinks 2)))

(defn- reading-resident-lives-next-to-fox-owner?
  [{:keys [hobbies pets]}]
  (lives-next-to? (house-number hobbies :reading)
                  (house-number pets :fox)))

(defn- painters-house-next-to-house-with-horse?
  [{:keys [hobbies pets]}]
  (lives-next-to? (house-number hobbies :painting)
                  (house-number pets :horse)))

(defn- football-player-drinks-orange-juice?
  [{:keys [hobbies drinks]}]
  (= :orange-juice (nth drinks (house-number hobbies :football))))

(defn- japanese-resident-plays-chess?
  [{:keys [nationalities hobbies]}]
  (= :chess (nth hobbies (house-number nationalities :japanese))))

(defn- norwegian-lives-next-to-blue-house?
  [{:keys [nationalities colors]}]
  (lives-next-to? (house-number nationalities :norwegian)
                  (house-number colors :blue)))

;; ── Solving the puzzle ───────────────────────────────────────────────
;; Assign one domain at a time, keeping only orderings that satisfy every
;; statement whose domains are already placed.

(def the-street
  (first
   (for [nationalities (permutations nationalities)
         :let [street {:nationalities nationalities}]
         :when (norwegian-lives-in-first-house? street)
         colors (permutations colors)
         :let [street (assoc street :colors colors)]
         :when (and (englishman-lives-in-red-house? street)
                    (green-house-immediately-right-of-ivory-house? street)
                    (norwegian-lives-next-to-blue-house? street))
         drinks (permutations drinks)
         :let [street (assoc street :drinks drinks)]
         :when (and (green-house-resident-drinks-coffee? street)
                    (ukrainian-drinks-tea? street)
                    (middle-house-resident-drinks-milk? street))
         hobbies (permutations hobbies)
         :let [street (assoc street :hobbies hobbies)]
         :when (and (yellow-house-resident-is-a-painter? street)
                    (japanese-resident-plays-chess? street)
                    (football-player-drinks-orange-juice? street))
         pets (permutations pets)
         :let [street (assoc street :pets pets)]
         :when (and (spaniard-owns-the-dog? street)
                    (snail-owner-likes-to-go-dancing? street)
                    (painters-house-next-to-house-with-horse? street)
                    (reading-resident-lives-next-to-fox-owner? street))]
     street)))

(defn- houses
  "The five houses of the street, each a map of its attributes."
  [{:keys [nationalities colors pets drinks hobbies]}]
  (map (fn [nationality color pet drink hobby]
         {:nationality nationality, :color color, :pet pet,
          :drink drink, :hobby hobby})
       nationalities colors pets drinks hobbies))

(defn- resident-who
  [house-pred]
  (:nationality (first (filter house-pred (houses the-street)))))

(defn drinks-water
  "Returns who drinks water."
  []
  (resident-who #(= :water (:drink %))))

(defn owns-zebra
  "Returns who owns the zebra."
  []
  (resident-who #(= :zebra (:pet %))))
