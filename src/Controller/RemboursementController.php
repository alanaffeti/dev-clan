<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Remboursement;
use App\Repository\RemboursementRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;
use App\Form\RemboursementType;

class RemboursementController extends AbstractController
{
    #[Route('/remboursement', name: 'app_remboursement')]
    public function ajoutremboursement(): Response
    {
        return $this->render('remboursement/ajoutremboursement.html.twig', [
            'controller_name' => 'RemboursementController',
        ]);
    } 
    
    #[Route('/addremboursement', name: 'addremboursement')]
    public function addremboursement(ManagerRegistry $doctrine,
    Request $request){
    $remboursement= new remboursement();
    $form=$this->createForm(remboursementType::class,
    $remboursement);
    $form->handleRequest($request);
    // Send SMS to client
    $accountSid ='ACc2d3dda9a42edc237090e304e24eb3fb';
    $authToken = 'aff947fd54bec6e3946ad875ed19acce';
    $twilioNumber = "+15673523812";
    $client = new Client($accountSid, $authToken);
    $message = $client->messages->create(
        '+21655543399', // Client's phone number
        [
            'from' => $twilioNumber,
            'body' => 'Pour suivre le traitement de votre constat , merci de vérifier votre boite mail ' ,
        ]
    );
    //Action d'ajout
    $mail = new PHPMailer(true);
    if($form->isSubmitted() && $form->isValid()){
    $em =$doctrine->getManager() ;
    $em->persist($remboursement);
    $em->flush();
    try {
            
        $time = date('H:i:s \O\n d/m/Y');
       
        $montant_remb= $form->get('montant_remb')->getData();
      

       
        $mail->SMTPDebug = SMTP::DEBUG_SERVER;
        $mail->isSMTP();
        $mail->Host       = 'smtp.gmail.com';
        $mail->SMTPAuth   = true;
        $mail->Username   = 'seifeddine.elfen@esprit.tn';            
        $mail->Password   = '223JMT2627';                               
        $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
        $mail->Port       = 587;
        $mail->addAddress( 'seifeddine.elfen@esprit.tn');     
        $mail->isHTML(true);                                  
        $mail->Subject = " Assurensea  ";
        $mail->Body    = "Bonjour Madame / Monsieur , Nous avons términer le traitement de votre contat . Le remboursement a été bien effectué avec succées . 
        En espérant conserver votre confiance pour une prochaine commande,
         nous vous prions d’agréer, Madame / Monsieur, l’expression de nos sincères salutations.";

        $mail->send();
        $this->addFlash('message','the email has been sent');
        $flashy->success('Event created!');
        
    } catch (Exception $e) {
        echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
    }
    return $this->redirectToRoute("afficheR");}
    return $this->renderForm("remboursement/ajoutremboursement.html.twig",
    array("ajout"=>$form));
    }
    
    #[Route('/modifremboursement/{id}', name: 'modifremboursement')]
    public function modifremboursement(ManagerRegistry $doctrine,
    Request $request, $id ,RemboursementRepository $r){
    $remboursement= $r->find($id);
    $form=$this->createForm(RemboursementType::class,
    $remboursement);
    $form->handleRequest($request);
    //Action update
    if($form->isSubmitted()){
    $em =$doctrine->getManager() ;
    $em->flush();
    return $this->redirectToRoute("afficheR");}
    return $this->renderForm("remboursement/ajoutremboursement.html.twig",
    array("ajout"=>$form));
    }
    
    #[Route('/afficheR', name: 'afficheR')]
    public function afficheC(): Response
    {
        $r=$this->getDoctrine()->getRepository(remboursement::class);
        $c=$r->findall();


        return $this->render('remboursement/affichage.html.twig', [
            'remboursements' => $c,
        ]);
    }
    #[Route('/suppremboursement/{id}', name: 'suppremboursement')]
    public function suppC(RemboursementRepository $r, $id, ManagerRegistry $M): Response
    {
    
        $remboursement=$r->find($id);
        //recup entity manager
        $em=$M->getManager();
        //action de suppression

        $em->remove($remboursement);
        $em->flush();



        return $this->redirectToRoute('afficheR');
    }
    #[Route('/rechercheR', name: 'rechercheR')]
    public function rechercheR(Request $request): Response
    {
        $entityManager = $this->getDoctrine()->getManager();
        $remboursements = $entityManager->getRepository(Remboursement::class)->findAll();
    
        if ($request->isMethod('POST')) {
            $date = $request->request->get('date_remb');
            $remboursements = $entityManager->getRepository(Remboursement::class)->findBy(['date_remb' => $date]);
        }
    
        return $this->render('remboursement/recherche.html.twig', [
            'remboursement' => $remboursements,
        ]);
    }

    #[Route("/AllFactures", name: "all")]
    //* Dans cette fonction, nous utilisons les services NormlizeInterface et FactureRepository, 
    //* avec la méthode d'injection de dépendances.
     public function getFactures(FactureRepository $repo, NormalizerInterface $normalizer)
    {
        $factures = $repo->findAll();
        //* Nous utilisons la fonction normalize qui transforme le tableau d'objets 
        //* factures en  tableau associatif simple.
        $facturesNormalises = $normalizer->normalize($factures, 'json', ['groups' => "factures"]);

        // //* Nous utilisons la fonction json_encode pour transformer un tableau associatif en format JSON
        $json = json_encode($facturesNormalises);

        //$json = $serializer->serialize($factures, 'json', ['groups' => "factures"]);

        //* Nous renvoyons une réponse Http qui prend en paramètre un tableau en format JSON
        return new Response($json);
    }

    #[Route("addfactureJSON/new", name: "addfactureJSON")]
    public function addfacturesJSON(Request $req,NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $facture = new facture();
        $facture->setQuantite($req->get('quantite'));
        $facture->setNomPiece($req->get('Nom_piece'));
        $facture->setNomClient($req->get('Nom_client'));
        $facture->setIdClient($req->get('id_client'));

        $facture->setMontantFacture($req->get('montant_facture'));
        $em->persist($facture);
        $em->flush();

        $jsonContent = $Normalizer->normalize($facture, 'json', ['groups' => 'facture']);
        return new Response(json_encode($jsonContent));
    }
    

    
    
}    

    

