package fr.icecubefr.secretSanta.modele;

import java.util.*;

public class SecretManager {
    List<String> santas;
    List<String> receivers;

    public SecretManager() {
        this.santas = new ArrayList<String>();
        this.receivers = new ArrayList<String>();
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public List<String> getSantas() {
        return santas;
    }

    public void addRandomDuo(Map<String, String> duos) {
        Random r = new Random();
        String santa = this.santas.get(r.nextInt(this.santas.size()));
        String receiver = this.receivers.get(r.nextInt(this.receivers.size()));
        int ttl = 50;
        while((santa.equals(receiver) || santa.equals(duos.get(receiver))) && ttl > 0) {
            receiver =  this.receivers.get(r.nextInt(this.receivers.size()));
            ttl--;
        }
        duos.put(santa, receiver);
        this.santas.remove(santa);
        this.receivers.remove(receiver);
    }

    public void addParticipant(String pers) {
        this.santas.add(pers);
        this.receivers.add(pers);
    }

    public void removeParticipant(String pers) {
        this.santas.remove(pers);
        this.receivers.remove(pers);
    }

    public Map<String, String> launchSecretSanta() {
        String[] backSanta = this.getSantas().toArray(new String[0]);
        String[] backReceiver = this.getReceivers().toArray(new String[0]);
        Map<String, String> secret = new HashMap<String, String>();
        int participants = this.santas.size();
        for (int i = 0; i < participants; i++) {
            addRandomDuo(secret);
        }
        this.santas.addAll(Arrays.asList(backSanta));
        this.receivers.addAll(Arrays.asList(backReceiver));
        return secret;
    }
}
