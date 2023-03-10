<?php

namespace App\Controller;

use App\Entity\Reclamation;
use App\Form\ReclamationType;
use App\Repository\ReclamationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;
use App\Service\BadWordsFilter;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\Material\BarChart;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\PieChart;
use Doctrine\ORM\Query\Expr\Func;
use Doctrine\ORM\Query\Expr;
use Doctrine\ORM\Query\ResultSetMapping;
use Twilio\Rest\Client;







    #[Route('/reclamation')]
class ReclamationController extends AbstractController
{
    #[Route('/', name: 'app_reclamation_index', methods: ['GET'])]
    public function index(ReclamationRepository $ReclamationRepository,Request $request): Response
    {
        $reclamation = new Reclamation();
        $form1 = $this->createForm(ReclamationType::class,$reclamation);
        $form1->handleRequest($request);
       //initialement le tableau des reclamation est vide, 
       //c.a.d on affiche les reclamation que lorsque l'utilisateur clique sur le bouton rechercher
        $reclamation= [];
        
       if($form1->isSubmitted() && $form1->isValid()) {
       //on récupère le nom d'article tapé dans le formulaire
        $nom = $propertySearch->getNom();   
        if ($nom!="") 
          //si on a fourni un nom d'article on affiche tous les reclamation ayant ce nom
          $reclamation= $this->getDoctrine()->getRepository(ReclamationType::class)->findBy(['nom' => $nom] );
        else   
          //si si aucun nom n'est fourni on affiche tous les reclamation
          $reclamation= $this->getDoctrine()->getRepository(ReclamationTypes::class)->findAll();
       }
        return  $this->render('reclamation/index.html.twig',[ 'form1' =>$form1->createView(), 'reclamations' => $ReclamationRepository->findAll()]);  
      
    }
    #[Route('/search', name: 'search_reclamation')]

    public function search(Request $request, ReclamationRepository $repository): Response
    {
       

        $query = $request->query->get('query');
        $reclamation = $repository->findBySearchQuery($query);


        return $this->render('reclamation/index.html.twig', [
            'query' => $query,
            'reclamations' => $reclamation
           
        ]);
    }




    
     /**
     * @Route("/statistic", name="statis")
     */

    
     


        //$ach = $ReclamationRepository->sewy();
       /* $date_rec = '2023-03-09'; // Remplacez ceci par la date à rechercher
        $nbreDatesSimilaires = $ReclamationRepository->sewy($date_rec);
        $total = $ReclamationRepository->count([]);
         $pieChart = new PieChart();
         $pieChart->getData()->setArrayToDataTable(
            // [['reclamation', 'date_rec'],
//['date_rec 1', intVal($ach)],
['Reclamation', 'Nombre'],
['Dates similaires', intval($nbreDatesSimilaires)],
['Autres dates', intval($total - $nbreDatesSimilaires)],

         );
         $pieChart->getOptions()->setTitle('Suiiiiiiiiiii'.$date_rec);
         $pieChart->getOptions()->setHeight(500);
         $pieChart->getOptions()->setWidth(900);
         $pieChart->getOptions()->getTitleTextStyle()->setBold(true);
         $pieChart->getOptions()->getTitleTextStyle()->setColor('#009900');
         $pieChart->getOptions()->getTitleTextStyle()->setItalic(true);
         $pieChart->getOptions()->getTitleTextStyle()->setFontName('Arial');
         $pieChart->getOptions()->getTitleTextStyle()->setFontSize(20);*/
 

    
          
        /*return $this->render('reclamation/index.html.twig', [
            'reclamations' => $reclamationRepository->findAll(),
        ]);
    }*/
    
    
    /**
     * @Route("/stats", name="stats")
     */
    public function statisreclamation(ReclamationRepository $ReclamationRepository)
{
      //on va chercher les categories
      $rech = $ReclamationRepository->barDep();
      $arr = $ReclamationRepository->barArr();
      
      $bar = new barChart ();
      $bar->getData()->setArrayToDataTable(
          [['Reclamation', 'Reclamation
          '],
           ['1', intVal($rech)],
           ['2', intVal($arr)],
          
  
          ]
      );
  
      $bar->getOptions()->setTitle('les Reclamations');
      $bar->getOptions()->getHAxis()->setTitle('Nombre de reclamation');
      $bar->getOptions()->getHAxis()->setMinValue(0);
      $bar->getOptions()->getVAxis()->setTitle('Type');
      $bar->getOptions()->SetWidth(800);
      $bar->getOptions()->SetHeight(400);
  
  
      return $this->render('/stats.html.twig', array('bar'=> $bar )); 
    }
       
  
  
    /*public function statistiques(ReclamationRepository $ReclamRepos){
        // On va chercher toutes les catégories
        $reclamation = $ReclamRepos->findAll();

        $ReclamReposNom = [];
        $ReclamReposColor = [];
        $ReclamReposCount = [];

        // On "démonte" les données pour les séparer tel qu'attendu par ChartJS
        foreach($reclamation as $rec){
            $ReclamReposNom[] = $rec->getNom();
            $ReclamReposColor[] = $rec->getCouleur();
            $ReclamReposCount[] = count($rec->getTypeRec());
        }

        return $this->render('/stats.html.twig', [
            'ReclamReposNom' => json_encode($ReclamReposNom),
            'ReclamReposColor' => json_encode($ReclamReposColor),
            'ReclamReposCount' => json_encode($ReclamReposCount), ]); }
/*
         /**
     * @Route("/searchrec", name="search", methods={"GET"})
     */
   /* public function searchrec(Request $request, NormalizerInterface $Normalizer)
    {
        $reclamation=($this->getDoctrine()->getRepository(Reclamation::class)->findBy(['Nom' => $request->get('search')])
            
            

        );
        dump($request->get('search'));
        if (null != $request->get('search')) {
            return $this->render('/reclamation/index.html.twig', [
                'reclamation' => $reclamation,
                

            ]);
        }
    }*/
    
   /*  public function statistiques(ReclamationRepository $reclamationRepository){
       
      $reclamation=$reclamationRepository->findAll();

      $reclamNom = [];
      $reclamPrenom = [];
      $reclamDateRec = [];
       // On "démonte" les données pour les séparer tel qu'attendu par ChartJS
       foreach($reclamation as $rec){
        $reclamNom[] = $rec->getNom();
        $reclamPrenom[] = $rec->getPrenom();
        $reclamTypeRec[] = $rec->getTypeRec();
        $reclamDateRec[] =  $rec->getDateRec();
    }

       
    return $this->render('/stats.html.twig', [
        'reclamNom' => json_encode($reclamNom),
        'reclamPrenom' => json_encode($reclamPrenom),
        'reclamDateRec' => json_encode($reclamDateRec),
        'reclamTypeRec' => json_encode($reclamTypeRec),

        //'recdates' => json_encode($recdates),



    ]);
}*/
        /*$reclamDateRec[] = count($rec->getTypeRec());*/

         // On va chercher le nombre d'annonces publiées par date
        /* $reclamation = $reclamationRepository->countByDate();

         $date_rec = [];
         $RecCount = [];
 
         // On "démonte" les données pour les séparer tel qu'attendu par ChartJS
         foreach($reclamation as $rec){
             $date_rec[] = $reclamation['date_rec'];
             $RecCount[] = $rec['count'];
         }*/
    
   /* #[Route('/search', name: 'app_search')]
    public function search(Request $request)
    {
        return $this->render('search/reclamation.html.twig');
            
    }*/
/*#[Route('/', name: 'app_reclamation_index', methods: ['GET','POST'])]
       public function new(Request $request, BadWordsFilter $badWordsFilter): Response
    {
        $description = new  description();
        $form = $this->createForm(descriptionType::class, $description);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $description->setText($badWordsFilter->filter($description->getText()));
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($description);
            $entityManager->flush();
            return $this->redirectToRoute('description_index');
        }

        return $this->render('description/new.html.twig', [
            'description' => $description,
            'form' => $form->createView(),
        ]);
    }
/*

    /*
class CommentController extends AbstractController
{
    /**
     * @Route("/comment/new", name="comment_new", methods={"GET","POST"})
     */
    /*public function new(Request $request, BadWordsFilter $badWordsFilter): Response
    {
        $comment = new Comment();
        $form = $this->createForm(CommentType::class, $comment);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $comment->setText($badWordsFilter->filter($comment->getText()));
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($comment);
            $entityManager->flush();

            return $this->redirectToRoute('comment_index');
        }

        return $this->render('comment/new.html.twig', [
            'comment' => $comment,
            'form' => $form->createView(),
        ]);
    }
}
/*
    #[Route('/', name: 'app_reclamation_index', methods: ['GET','POST'])]
    public function index(ReclamationRepository $reclamationRepository,Request $request): Response
    {
$propertySearch = new PropertySearch();
$form = $this->createForm(PropertySearchType::class,$propertySearch);
$form->handleRequest($request);


$reclamation= $reclamationRepository->findAll();

if ($form->isSubmitted() && $form->isValid()) {
    $nom = $propertySearch->getNom();
    if ($nom != "")
        $reclamation = $this->getDoctrine()->getRepository(Reclamation::class)->findBy(['email' => $nom]);
    else
        $reclamation = $this->getDoctrine()->getRepository(Reclamation::class)->findAll();
}
        return $this->render('reclamation/index.html.twig', [
            'form' => $form->createView(),'reclamations'=>$reclamation]);
    }
*/

    #[Route('/new', name: 'app_reclamation_new', methods: ['GET', 'POST'])]
    public function new(Request $request, ReclamationRepository $reclamationRepository): Response
    {
        $reclamation = new Reclamation();
        $reclamation->setDateRec(new \DateTime());
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);
        $mail = new PHPMailer(true);

        if ($form->isSubmitted() && $form->isValid()) {
            $reclamationRepository->save($reclamation, true);
            try {
            
                $time = date('H:i:s \O\n d/m/Y');
               
                $nom = $form->get('nom')->getData();
                $prenom = $form->get('prenom')->getData();

                $email = $form->get('email')->getData();
    
               
                $mail->SMTPDebug = SMTP::DEBUG_SERVER;
                $mail->isSMTP();
                $mail->Host       = 'smtp.gmail.com';
                $mail->SMTPAuth   = true;
                $mail->Username   = 'akrem.mahmoud@esprit.tn';            
                $mail->Password   = '223JMT2956';                               
                $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
                $mail->Port       = 587;
                $mail->addAddress( 'akrem.mahmoud@esprit.tn');     
                $mail->isHTML(true);                                  
                $mail->Subject = " Assurensea   ";
                $mail->Body    = "Heureux de recevoir votre Reclamation  ";
    
                $mail->send();
               
            } catch (Exception $e) {
                echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
            }
            $this->addFlash('success', 'Reclamation Ajoutée!');

            
            return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('reclamation/new.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form,
            'message'=>$this,

        ]);
    }
    
    #[Route("/AllReclamation", name: "all")]
    //* Dans cette fonction, nous utilisons les services NormlizeInterface et StudentRepository, 
    //* avec la méthode d'injection de dépendances.
     public function getReclamation(ReclamationRepository $repo, NormalizerInterface $normalizer)
    {
        $reclamations = $repo->findAll();
        //* Nous utilisons la fonction normalize qui transforme le tableau d'objets 
        //* students en  tableau associatif simple.
        $reclamationNormalises = $normalizer->normalize($reclamations, 'json', ['groups' => "reclamations"]);

        // //* Nous utilisons la fonction json_encode pour transformer un tableau associatif en format JSON
        $json = json_encode($reclamationNormalises);

        //$json = $serializer->serialize($voitures, 'json', ['groups' => "reclamation"]);

        //* Nous renvoyons une réponse Http qui prend en paramètre un tableau en format JSON
        return new Response($json);
    }
    

    #[Route('/{id}', name: 'app_reclamation_show', methods: ['GET'])]
    public function show(Reclamation $reclamation): Response
    {
        return $this->render('reclamation/show.html.twig', [
            'reclamation' => $reclamation,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_reclamation_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Reclamation $reclamation, ReclamationRepository $reclamationRepository): Response
    {
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $reclamationRepository->save($reclamation, true);

            return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('reclamation/edit.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form,
        ]);
    }
    
    #[Route('/{id}', name: 'app_reclamation_delete', methods: ['POST'])]
    public function delete(Request $request, Reclamation $reclamation, ReclamationRepository $reclamationRepository): Response
    {
        // Send SMS to client
        $accountSid ='AC4e4fedbf3c5b6e032a7fcdf83b2a7ba5';
        $authToken = 'b2a2cb44a9904b77db3df1665916cc59';
        $twilioNumber = "+15673612899";
        $client = new Client($accountSid, $authToken);
        $message = $client->messages->create(
            '+21655638527', // Client's phone number
            [
                'from' => $twilioNumber,
                'body' => 'Votre réclamation a été supprimé avec Succés(Reclamation de Date : '. $reclamation->getDateRec()->format('m/d/Y') ,
            ]
        );
        if ($this->isCsrfTokenValid('delete'.$reclamation->getId(), $request->request->get('_token'))) {
            $reclamationRepository->remove($reclamation, true);
        }

        return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
    }
    
    
   
  
}
